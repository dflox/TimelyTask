package me.timelytask.util.serialization.decoder

import io.circe.{Decoder, Json}
import me.timelytask.core.{StartUpConfig, UIInstanceConfig}
import me.timelytask.model.settings.UIType
import me.timelytask.util.serialization.TypeDecoder

given TypeDecoder[StartUpConfig] with {
  private val uiTypeDecoder: Decoder[UIType] = Decoder.forProduct1[UIType, String]("uiType")(
    s => UIType.fromString(s))

  private val uiInstanceConfigDecoder: Decoder[UIInstanceConfig] = Decoder
    .forProduct1[UIInstanceConfig, List[UIType]]
    ("uis")(typeList => UIInstanceConfig(typeList))(Decoder.decodeList(uiTypeDecoder))

  private val decoder: Decoder[StartUpConfig] = Decoder
    .forProduct2[StartUpConfig, List[UIInstanceConfig], String]("uiInstances", "serializationType")(
      (uis: List[UIInstanceConfig], serializationType: String) => StartUpConfig(uis,
        serializationType))
    (Decoder.decodeList[UIInstanceConfig](uiInstanceConfigDecoder))

  def apply(json: Json): Option[StartUpConfig] = {
    json.as[StartUpConfig](decoder) match {
      case Right(startUpConfig) => Some(startUpConfig)
      case Left(_) => None
    }
  }
}