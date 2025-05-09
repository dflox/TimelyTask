package me.timelytask.util.serialization.serializer

import io.circe.yaml.v12.parser
import io.circe.yaml.v12.syntax.AsYaml
import me.timelytask.util.serialization.{SerializationStrategy, TypeDecoder, TypeEncoder}

class YamlSerializationStrategy extends SerializationStrategy {
  override def serialize[T](obj: T)(using typeEncoder: TypeEncoder[T]): String = {
    typeEncoder(obj).asYaml.spaces2
  }

  override def deserialize[T](str: String)(using typeSerializer: TypeDecoder[T]): Option[T] = {
    parser.parse(str) match {
      case Left(_) => None
      case Right(json) => typeSerializer(json)
    }
  }
}
