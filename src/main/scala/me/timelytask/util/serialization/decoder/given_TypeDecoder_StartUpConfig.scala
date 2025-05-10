package me.timelytask.util.serialization.decoder

import io.circe.{Decoder, Json}
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.settings.UIType
import me.timelytask.util.serialization.TypeDecoder

given TypeDecoder[StartUpConfig] with {
  private val uiTypeDecoder: Decoder[UIType] = Decoder.forProduct1[UIType, String]("uiType")(
    s => UIType.fromString(s))

  private val uiInstanceConfigDecoder: Decoder[UiInstanceConfig] = Decoder
    .forProduct1[UiInstanceConfig, List[UIType]]
    ("uis")(typeList => UiInstanceConfig(typeList))(Decoder.decodeList(uiTypeDecoder))

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