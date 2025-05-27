package me.timelytask.util.serialization.serializer

import io.circe.{Json, parser}
import me.timelytask.util.serialization.{SerializationStrategy, TypeDecoder, TypeEncoder}

class JsonSerializationStrategy extends SerializationStrategy{
  override def serialize[T](obj: T)(using typeEncoder: TypeEncoder[T]): String = {
    typeEncoder(obj).spaces2
  }

  override def deserialize[T](str: String)(using typeDecoder: TypeDecoder[T]): Option[T] = {
    parser.parse(str) match
      case Left(_) => None
      case Right(json) => typeDecoder(json)
  }
}