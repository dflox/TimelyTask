package me.timelytask.util.serialization.encoder

import io.circe.{Encoder, Json}
import me.timelytask.core.{StartUpConfig, UIInstanceConfig}
import me.timelytask.model.settings.UIType
import me.timelytask.util.serialization.TypeEncoder

given TypeEncoder[StartUpConfig] with {
  private val uiTypeEncoder: Encoder[UIType] = Encoder.forProduct1[UIType, String]("uiType")(
    s => s.toString)()

  private val uiInstanceConfigEncoder: Encoder[UIInstanceConfig] = Encoder
    .forProduct1[UIInstanceConfig, List[UIType]]
    ("uis")(c => c.uis)(Encoder.encodeList(uiTypeEncoder))

  private val encoder: Encoder[StartUpConfig] = Encoder
    .forProduct1[StartUpConfig, List[UIInstanceConfig]]("uiInstances")(
      s => s.uiInstances)
    (Encoder.encodeList[UIInstanceConfig](uiInstanceConfigEncoder))

  def apply(obj: StartUpConfig): Json = encoder.apply(obj)
}