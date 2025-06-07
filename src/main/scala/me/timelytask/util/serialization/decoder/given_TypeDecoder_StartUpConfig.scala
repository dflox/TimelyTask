package me.timelytask.util.serialization.decoder

import io.circe.{Decoder, Json}
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.settings.{UIType, ViewType}
import me.timelytask.util.serialization.TypeDecoder

given TypeDecoder[StartUpConfig] with {
  private val uiTypeDecoder: Decoder[UIType] = Decoder.forProduct1[UIType, String]("uiType")(
    s => UIType.fromString(s))

  private implicit val viewTypeDecoder: Decoder[ViewType] = Decoder.decodeString.emap { str =>
    try {
      Right(ViewType.fromString(str))
    } catch {
      case e: IllegalArgumentException => Left(s"Invalid ViewType: $str. ${e.getMessage}")
      case e: Exception => Left(s"Error decoding ViewType: $str. ${e.getMessage}")
    }
  }

  private val uiInstanceConfigDecoder: Decoder[UiInstanceConfig] = Decoder
    .forProduct2[UiInstanceConfig, List[UIType], ViewType]
    ("uis", "startView")((typeList, startView) => UiInstanceConfig(typeList, startView))(Decoder.decodeList(uiTypeDecoder))

  private val decoder: Decoder[StartUpConfig] = Decoder
    .forProduct1[StartUpConfig, List[UiInstanceConfig]]("uiInstances")(
      (uis: List[UiInstanceConfig]) => StartUpConfig(uis))
    (Decoder.decodeList[UiInstanceConfig](uiInstanceConfigDecoder))

  def apply(json: Json): Option[StartUpConfig] = {
    json.as[StartUpConfig](decoder) match {
      case Right(startUpConfig) => Some(startUpConfig)
      case Left(_) => None
    }
  }
}