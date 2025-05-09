package me.timelytask.util.serialization

import me.timelytask.util.serialization.serializer._

trait SerializationStrategy {
  def serialize[T](obj: T)(using typeEncoder: TypeEncoder[T]): String
  def deserialize[T](str: String)(using typeSerializer: TypeDecoder[T]): Option[T]
}

object SerializationStrategy {
  def apply(strategy: String): SerializationStrategy = {
    strategy match {
      case "json" => JsonSerializationStrategy()
      case "yaml" => YamlSerializationStrategy()
      case _ => throw new IllegalArgumentException(s"Unknown serialization strategy: $strategy")
    }
  }
}