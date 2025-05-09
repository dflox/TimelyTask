package me.timelytask.util.serialization.serializer

import io.circe.{Decoder, Encoder, Json}
import me.timelytask.core.{StartUpConfig, UIInstanceConfig}
import me.timelytask.model.settings.UIType
import me.timelytask.util.serialization.{SerializationStrategy, TypeDecoder, TypeEncoder}

class JsonSerializationStrategy extends SerializationStrategy{
  override def serialize[T](obj: T)(using typeEncoder: TypeEncoder[T]): String = {
    typeEncoder(obj).spaces2
  }

  override def deserialize[T](str: String)(using typeSerializer: TypeDecoder[T]): Option[T] = {
    val json = io.circe.parser.parse(str)
    json match
      case Left(_) => None
      case Right(json) => typeSerializer(json)
  }
}