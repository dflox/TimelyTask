package me.timelytask.util.serialization.encoder

import io.circe.{Encoder, Json}
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.settings.{UIType, ViewType}
import me.timelytask.util.serialization.TypeEncoder

given TypeEncoder[StartUpConfig] with {
  private val uiTypeEncoder: Encoder[UIType] = Encoder.forProduct1[UIType, String]("uiType")(
    s => s.toString)()
  
    private implicit val viewTypeEncoder: Encoder[ViewType] = Encoder.encodeString.contramap(_.toString)

  private val uiInstanceConfigEncoder: Encoder[UiInstanceConfig] = Encoder
    .forProduct2[UiInstanceConfig, List[UIType], ViewType]
    ("uis", "startView")(c => (c.uis, c.startView))
    (Encoder.encodeList(uiTypeEncoder), viewTypeEncoder)

  private val encoder: Encoder[StartUpConfig] = Encoder
    .forProduct1[StartUpConfig, List[UiInstanceConfig]]("uiInstances")(
      s => s.uiInstances)
    (Encoder.encodeList[UiInstanceConfig](uiInstanceConfigEncoder))

  def apply(obj: StartUpConfig): Json = encoder.apply(obj)
}