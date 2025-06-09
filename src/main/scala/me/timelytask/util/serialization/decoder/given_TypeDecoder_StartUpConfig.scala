package me.timelytask.util.serialization.decoder

import io.circe.{Decoder, Json, KeyDecoder}
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.{Config, Model, Priority, Tag, Task}
import me.timelytask.model.settings.{EventTypeId, FileType, KeymapConfig, Theme, UIType, ViewType}
import me.timelytask.model.state.TaskState
import me.timelytask.model.utility.Key
import me.timelytask.util.serialization.TypeDecoder

import scala.collection.immutable.HashSet
extension (d: Decoder.type ){
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

  def uiInstanceConfig: Decoder[UiInstanceConfig] = Decoder.forProduct2[UiInstanceConfig, List[UIType], ViewType](
    "uis", "startView"
  )(
    (typeList, startView) => UiInstanceConfig(typeList, startView)
  )(
    Decoder.decodeList(uiType), Decoder.viewType
  )

  def startUpConfig: Decoder[StartUpConfig] = Decoder.forProduct1[StartUpConfig, List[UiInstanceConfig]](
      "uiInstances"
    )(
      (uis: List[UiInstanceConfig]) => StartUpConfig(uis)
    )(
    Decoder.decodeList[UiInstanceConfig](Decoder.uiInstanceConfig)
  )

  def key: Decoder[Key] = Decoder.decodeString.emap { str =>
    try {
      Right(Key.fromString(str))
    } catch {
      case e: IllegalArgumentException => Left(s"Invalid Key: $str. ${e.getMessage}")
      case e: Exception => Left(s"Error decoding Key: $str. ${e.getMessage}")
    }
  }


  def keymapConfig: Decoder[KeymapConfig] = Decoder.forProduct1[KeymapConfig, Map[Key, EventTypeId]](
    "mappings"
  )(
    KeymapConfig.apply
  )(
    Decoder.decodeMap(Decoder.key, Decoder.eventTypeId)
  )

  def config: Decoder[Config] = Decoder.forProduct5[Config, Map[ViewType, KeymapConfig],
    KeymapConfig, ViewType, FileType, Theme](
    "keymaps", "globalKeymap", "defaultStartView", "defaultDataFileType", "defaultTheme"
  )(
    Config.apply
  )(
    Decoder.decodeMap(Decoder.viewTypeKeyDecoder, Decoder.keymapConfig),
    KeymapConfig.decoder,
    viewType,
    FileType.decoder,
    Theme.decoder
  )

  def model: Decoder[Model] = Decoder.forProduct5[Model, List[Task], HashSet[Tag],
    HashSet[Priority], HashSet[TaskState], Config](
    "tasks", "tags", "priorities", "states", "config"
  )(
    Model.apply
  )(
    Decoder.decodeList(Decoder.task),
    Decoder.decodeSet(Decoder.tag),
    Decoder.decodeSet(Decoder.priority),
    Decoder.decodeSet(Decoder.taskState),
    Decoder.config
  )
}

given TypeDecoder[StartUpConfig] with {
  def apply(json: Json): Option[StartUpConfig] = {
    json.as[StartUpConfig](Decoder.startUpConfig) match {
      case Right(startUpConfig) => Some(startUpConfig)
      case Left(_) => None
    }
  }
}

given TypeDecoder[Model] with {
  def apply(json: Json): Option[Model] = {
    json.as[Model](Decoder.model) match {
      case Right(model) => Some(model)
      case Left(_) => None
    }
  }
}