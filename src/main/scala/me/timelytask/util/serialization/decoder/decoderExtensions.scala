package me.timelytask.util.serialization.decoder

import com.github.nscala_time.time.Imports.DateTime
import io.circe.{Decoder, KeyDecoder}
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.*
import me.timelytask.model.config.Config
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.priority.Priority
import me.timelytask.model.settings.*
import me.timelytask.model.state.TaskState
import me.timelytask.model.tag.Tag
import me.timelytask.model.task.Task
import me.timelytask.model.user.User
import me.timelytask.model.utility.Key
import org.joda.time.Period
import scalafx.scene.paint.Color

import java.util.UUID
import scala.collection.immutable.HashSet

extension (d: Decoder.type) {
  def viewType: Decoder[ViewType] = Decoder.decodeString.emap { str =>
    try {
      Right(ViewType.fromString(str))
    } catch {
      case e: IllegalArgumentException => Left(s"Invalid ViewType: $str. ${e.getMessage}")
      case e: Exception => Left(s"Error decoding ViewType: $str. ${e.getMessage}")
    }
  }

  def viewTypeKeyDecoder: KeyDecoder[ViewType] = KeyDecoder.instance { str =>
    try {
      Some(ViewType.fromString(str))
    } catch {
      case _: Exception => None
    }
  }

  def uiType: Decoder[UIType] = Decoder.forProduct1[UIType, String](
    "uiType"
  )(
    s => UIType.fromString(s)
  )

  def uiInstanceConfig: Decoder[UiInstanceConfig] = Decoder
    .forProduct2[UiInstanceConfig, List[UIType], ViewType](
      "uis", "startView"
    )(
      (typeList, startView) => UiInstanceConfig(typeList, startView)
    )(
      Decoder.decodeList(uiType), Decoder.viewType
    )

  def startUpConfig: Decoder[StartUpConfig] = Decoder
    .forProduct1[StartUpConfig, List[UiInstanceConfig]](
      "uiInstances"
    )(
      (uis: List[UiInstanceConfig]) => StartUpConfig(uis)
    )(
      Decoder.decodeList[UiInstanceConfig](Decoder.uiInstanceConfig)
    )

  def key: KeyDecoder[Key] = KeyDecoder.instance { str =>
    try {
      Some(Key.fromString(str))
    } catch {
      case e: Exception => None
    }
  }

  def eventTypeId: Decoder[EventTypeId] = Decoder.forProduct1[EventTypeId, String](
    "name"
  )(
    EventTypeId.apply
  )(
    Decoder.decodeString
  )

  def keymapConfig: Decoder[KeymapConfig] = Decoder
    .forProduct1[KeymapConfig, Map[Key, EventTypeId]](
      "mappings"
    )(
      KeymapConfig.apply
    )(
      Decoder.decodeMap(Decoder.key, Decoder.eventTypeId)
    )

  def fileType: Decoder[FileType] = Decoder.decodeString.emap { str =>
    try {
      Right(FileType.fromString(str))
    } catch {
      case e: IllegalArgumentException => Left(s"Invalid FileType: $str. ${e.getMessage}")
      case e: Exception => Left(s"Error decoding FileType: $str. ${e.getMessage}")
    }
  }

  def theme: Decoder[Theme] = Decoder.decodeString.emap { str =>
    try {
      Right(Theme.fromString(str))
    } catch {
      case e: IllegalArgumentException => Left(s"Invalid Theme: $str. ${e.getMessage}")
      case e: Exception => Left(s"Error decoding Theme: $str. ${e.getMessage}")
    }
  }

  def config: Decoder[Config] = Decoder.forProduct5[Config, Map[ViewType, KeymapConfig],
    KeymapConfig, ViewType, FileType, Theme](
    "keymaps", "globalKeymap", "startView", "exportFileType", "theme"
  )(
    Config.apply
  )(
    Decoder.decodeMap(Decoder.viewTypeKeyDecoder, Decoder.keymapConfig),
    Decoder.keymapConfig,
    Decoder.viewType,
    Decoder.fileType,
    Decoder.theme
  )
  
  def user: Decoder[User] = Decoder.forProduct1[User, String](
    "name"
  )(
    User.apply
  )(
    Decoder.decodeString
  )

  def tag: Decoder[Tag] = Decoder.forProduct3[Tag, String, String, UUID](
    "name", "description", "uuid"
  )(
    Tag.apply
  )(
    Decoder.decodeString,
    Decoder.decodeString,
    Decoder.decodeUUID
  )

  def decodeHashSet[T](implicit elementDecoder: Decoder[T]): Decoder[HashSet[T]] =
    Decoder.decodeList(elementDecoder).map(_.to(HashSet))

  def color: Decoder[Color] = Decoder.forProduct4[Color, Double, Double, Double, Double](
    "red", "green", "blue", "opacity"
  )(
    Color.color
  )(
    Decoder.decodeDouble,
    Decoder.decodeDouble,
    Decoder.decodeDouble,
    Decoder.decodeDouble
  )

  def taskState: Decoder[TaskState] = Decoder.forProduct5[TaskState, String, String, Color,
    String, UUID](
    "name", "description", "color", "stateType", "uuid"
  )(
    TaskState.apply
  )(
    Decoder.decodeString,
    Decoder.decodeString,
    Decoder.color,
    Decoder.decodeString,
    Decoder.decodeUUID
  )

  def priority: Decoder[Priority] = Decoder.forProduct7[Priority, String, String, Int, Color,
    Int, Boolean, UUID](
    "name", "description", "rank", "color", "daysPreDeadline", "postponable", "uuid"
  )(
    Priority.apply
  )(
    Decoder.decodeString,
    Decoder.decodeString,
    Decoder.decodeInt,
    Decoder.color,
    Decoder.decodeInt,
    Decoder.decodeBoolean,
    Decoder.decodeUUID
  )

  def dateTime: Decoder[DateTime] = Decoder.decodeString.emap { str =>
    try {
      Right(DateTime.parse(str))
    } catch {
      case e: IllegalArgumentException => Left(s"Invalid DateTime: $str. ${e.getMessage}")
      case e: Exception => Left(s"Error decoding DateTime: $str. ${e.getMessage}")
    }
  }

  def period: Decoder[Period] = Decoder.decodeString.emap { str =>
    try {
      Right(Period.parse(str))
    } catch {
      case e: IllegalArgumentException => Left(s"Invalid Period: $str. ${e.getMessage}")
      case e: Exception => Left(s"Error decoding Period: $str. ${e.getMessage}")
    }
  }

  def deadline: Decoder[Deadline] = Decoder.forProduct3[Deadline, DateTime, Option[DateTime],
    Option[DateTime]](
    "date", "initialDate", "completionDate"
  )(
    Deadline.apply
  )(
    Decoder.dateTime,
    Decoder.decodeOption(Decoder.dateTime),
    Decoder.decodeOption(Decoder.dateTime)
  )

  def task: Decoder[Task] = Decoder.forProduct13[Task,
    String,
    String,
    Option[UUID],
    HashSet[UUID],
    Deadline,
    DateTime,
    Option[UUID],
    Period,
    HashSet[UUID],
    Boolean,
    Period,
    UUID,
    Option[Period]](
    "name", "description", "priority", "tags", "deadline", "scheduleDate", "state", "tedDuration",
    "dependentOn", "reoccurring", "recurrenceInterval", "uuid", "realDuration"
  )(
    Task.apply
  )(
    Decoder.decodeString,
    Decoder.decodeString,
    Decoder.decodeOption(Decoder.decodeUUID),
    Decoder.decodeHashSet(Decoder.decodeUUID),
    Decoder.deadline,
    Decoder.dateTime,
    Decoder.decodeOption(Decoder.decodeUUID),
    Decoder.period,
    Decoder.decodeHashSet(Decoder.decodeUUID),
    Decoder.decodeBoolean,
    Decoder.period,
    Decoder.decodeUUID,
    Decoder.decodeOption(Decoder.period)
  )

  def model: Decoder[Model] = Decoder.forProduct6[Model, List[Task], HashSet[Tag],
    HashSet[Priority], HashSet[TaskState], Config, User](
    "tasks", "tags", "priorities", "states", "config", "user"
  )(
    Model.apply
  )(
    Decoder.decodeList(Decoder.task),
    Decoder.decodeHashSet(Decoder.tag),
    Decoder.decodeHashSet(Decoder.priority),
    Decoder.decodeHashSet(Decoder.taskState),
    Decoder.config,
    Decoder.user
  )
}