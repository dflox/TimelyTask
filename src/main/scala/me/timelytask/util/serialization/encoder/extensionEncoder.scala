package me.timelytask.util.serialization.encoder

import io.circe.{Encoder, KeyEncoder}
import me.timelytask.model.settings.ViewType
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.*
import me.timelytask.model.settings.*
import me.timelytask.model.state.TaskState
import me.timelytask.model.utility.Key
import org.joda.time.{DateTime, Period}
import scalafx.scene.paint.Color

import java.util.UUID
import scala.collection.immutable.HashSet

extension (e: Encoder.type) {
  def uiType: Encoder[UIType] = Encoder.forProduct1[UIType, String] ("uiType") (
  s => s.toString) ()

  def viewType: Encoder[ViewType] = Encoder.encodeString.contramap (_.toString)
  
  def viewTypeKeyEncoder: KeyEncoder[ViewType] = KeyEncoder.instance {
    viewType => viewType.toString
  }

  def uiInstanceConfig: Encoder[UiInstanceConfig] = Encoder.forProduct2[UiInstanceConfig, 
  List[UIType], ViewType](
  "uis", "startView"
)(
  c => (c.uis, c.startView)
)(Encoder.encodeList(uiType), Encoder.viewType)
  
  def startUpConfig: Encoder[StartUpConfig] = Encoder.forProduct1[ StartUpConfig, 
    List[UiInstanceConfig] ](
    "uiInstances"
  )(
    s => s.uiInstances
  )(
    Encoder.encodeList[UiInstanceConfig](Encoder.uiInstanceConfig)
  )
  
  def key: KeyEncoder[Key] = KeyEncoder.instance {
    key => key.toString
  } 
  
  def eventTypeId: Encoder[EventTypeId] = Encoder.forProduct1[ EventTypeId, String] (
    "name"
  )(
    e => e.name
  )
  
  def keymapConfig: Encoder[KeymapConfig] = Encoder.forProduct1[KeymapConfig, Map[Key, 
    EventTypeId]](
    "mappings"
  )(
    k => k.mappings
  )(
    Encoder.encodeMap(Encoder.key, Encoder.eventTypeId)
  )

  def fileType: Encoder[FileType] = Encoder.encodeString.contramap {
    s => s.toString
  }
  
  def theme: Encoder[Theme] = Encoder.encodeString.contramap {
    theme => theme.toString
  }
  
  def config: Encoder[Config] = Encoder.forProduct5[Config, Map[ViewType, KeymapConfig],
  KeymapConfig, ViewType, FileType, Theme](
    "keymaps", "globalKeymap", "startView", "exportFileType", "theme"
  )(
    c => (c.keymaps, c.globalKeymap, c.startView, c.dataFileType, c.theme)
  )(
    Encoder.encodeMap(Encoder.viewTypeKeyEncoder, Encoder.keymapConfig),
    Encoder.keymapConfig,
    Encoder.viewType,
    Encoder.fileType,
    Encoder.theme
  )
  
  def tag: Encoder[Tag] = Encoder.forProduct3[Tag, String, String, UUID](
    "name", "description", "uuid"
  )(
    t => (t.name, t.description, t.uuid)
  )(
    Encoder.encodeString, Encoder.encodeString, Encoder.encodeUUID
  )
  
  def encodeHashSet[T](implicit elementEncoder: Encoder[T]): Encoder[HashSet[T]] = 
    Encoder.encodeList[T].contramap(_.toList)
  
  def color: Encoder[Color] = Encoder.forProduct4[Color, Double, Double, Double, Double](
    "red", "green", "blue", "opacity"
  )(
    c => (c.red, c.green, c.blue, c.opacity)
  )(
    Encoder.encodeDouble, Encoder.encodeDouble, Encoder.encodeDouble, Encoder.encodeDouble
  )
  
  def taskState: Encoder[TaskState] = Encoder.forProduct5[TaskState, String, String, Color, 
    String, UUID](
    "name", "description", "color", "stateType", "uuid"
  )(
    s => (s.name, s.description, s.color, s.stateType, s.uuid)
  )(
    Encoder.encodeString, Encoder.encodeString, Encoder.color, Encoder.encodeString, Encoder.encodeUUID
  )
  
  def priority: Encoder[Priority] = Encoder.forProduct7[Priority, String, String, Int, 
    Color, Int, Boolean, UUID](
    "name", "description", "rank", "color", "daysPreDeadline", "postponable", "uuid"
  )(
    p => (p.name, p.description, p.rank, p.color, p.daysPreDeadline, p.postponable, p.uuid)
  )(
    Encoder.encodeString, Encoder.encodeString, Encoder.encodeInt, Encoder.color,
    Encoder.encodeInt, Encoder.encodeBoolean, Encoder.encodeUUID
  )
  
  def dateTime: Encoder[org.joda.time.DateTime] = Encoder.encodeString.contramap { dt =>
    dt.toString
  }
  
  def period: Encoder[Period] = Encoder.encodeString.contramap { p =>
    p.toString
  }
  
  def deadline: Encoder[Deadline] = Encoder.forProduct3[Deadline, DateTime, 
    Option[DateTime], Option[DateTime]](
    "date", "initialDate", "completionDate"
  )(
    d => (d.date, d.initialDate, d.completionDate)
  )(
        Encoder.dateTime, Encoder.encodeOption(Encoder.dateTime), Encoder.encodeOption(Encoder.dateTime)
      )
  
  def task: Encoder[Task] = Encoder.forProduct14[Task,
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
  Option[Period],
    Option[DateTime]](
    "name", "description", "priority", "tags", "deadline", "scheduleDate", "state",
    "tedDuration", "dependentOn", "reoccurring", "recurrenceInterval", "uuid", "realDuration", 
    "completionDate"
  )(
    t => (t.name, t.description, t.priority, t.tags, t.deadline, t.scheduleDate, t.state,
      t.tedDuration, t.dependentOn, t.reoccurring, t.recurrenceInterval, t.uuid,
      t.realDuration, t.completionDate)
  )(
    Encoder.encodeString, Encoder.encodeString, Encoder.encodeOption(Encoder.encodeUUID),
    Encoder.encodeHashSet(Encoder.encodeUUID), Encoder.deadline, Encoder.dateTime,
    Encoder.encodeOption(Encoder.encodeUUID), Encoder.period, Encoder.encodeHashSet(Encoder.encodeUUID),
    Encoder.encodeBoolean, Encoder.period, Encoder.encodeUUID,
    Encoder.encodeOption(Encoder.period), Encoder.encodeOption(Encoder.dateTime)
  )
  
  def model: Encoder[Model] = Encoder.forProduct5[Model, List[Task], HashSet[Tag], 
    HashSet[Priority], HashSet[TaskState], Config](
    "tasks", "tags", "priorities", "states", "config"
  )(
    m => (m.tasks, m.tags, m.priorities, m.states, m.config)
  )(
    Encoder.encodeList(Encoder.task), Encoder.encodeHashSet(Encoder.tag),
    Encoder.encodeHashSet(Encoder.priority), Encoder.encodeHashSet(Encoder.taskState),
    Encoder.config
  )
}