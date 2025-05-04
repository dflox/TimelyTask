package me.timelytask.util.serialization.json

import io.circe.{Encoder, Json}
import me.timelytask.core.{StartUpConfig, UIInstanceConfig}
import me.timelytask.model.settings.UIType
import me.timelytask.util.serialization.{SerializationStrategy, TypeSerializer}

class JsonSerializationStrategy extends SerializationStrategy{
  implicit val self: JsonSerializationStrategy = this

  override def deserialize[T](str: String): Option[T] = ???
  
  override def startUpConfigSerializer: TypeSerializer[StartUpConfig] = JsonStartUpConfigSerializer()
}

class JsonStartUpConfigSerializer(implicit jsonSerializationStrategy: JsonSerializationStrategy) extends TypeSerializer[StartUpConfig] {
  override def serialize(obj: StartUpConfig): String = {
    CirceStartUpConfigEncoder(obj).spaces2
  }

  override def deserialize(json: String): Option[StartUpConfig] = {
    // Implement deserialization logic here
    ???
  }
}


object CirceStartUpConfigEncoder {
  private val uiTypeEncoder: Encoder[UIType] = Encoder.forProduct1[UIType, String]("uiType")(
    s => s.toString)()

  private val uiInstanceConfigEncoder: Encoder[UIInstanceConfig] = Encoder
    .forProduct1[UIInstanceConfig, List[UIType]]
    ("uis")(c => c.uis)(Encoder.encodeList(uiTypeEncoder))
  
  private val encoder: Encoder[StartUpConfig] = Encoder
    .forProduct2[StartUpConfig, List[UIInstanceConfig], String]("uiInstances", "serializationType")(
      s => (s.uiInstances, s.serializationType))
    (Encoder.encodeList[UIInstanceConfig](uiInstanceConfigEncoder))
  
  def apply(obj: StartUpConfig): Json = encoder.apply(obj)
} 
