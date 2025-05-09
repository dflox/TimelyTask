package me.timelytask.util.serialization

import io.circe.Json
import me.timelytask.core.StartUpConfig
import me.timelytask.model.Model
import me.timelytask.util.serialization.serializer.JsonSerializationStrategy
import me.timelytask.util.serialization.decoder.given

trait SerializationStrategy {
  def serialize[T](obj: T)(using typeEncoder: TypeEncoder[T]): String
  def deserialize[T](str: String)(using typeSerializer: TypeDecoder[T]): Option[T]
}

object SerializationStrategy {
  def apply(strategy: String): SerializationStrategy = {
    strategy match {
      case "json" => JsonSerializationStrategy()
      case _ => throw new IllegalArgumentException(s"Unknown serialization strategy: $strategy")
    }
  }
}