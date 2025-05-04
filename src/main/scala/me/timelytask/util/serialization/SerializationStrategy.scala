package me.timelytask.util.serialization

import me.timelytask.core.StartUpConfig
import me.timelytask.util.serialization.json.JsonSerializationStrategy

trait SerializationStrategy {
  def serialize[T](obj: T): String = {
    obj match {
      case s: String => s
      case i: Int => i.toString
      case d: Double => d.toString
      case b: Boolean => b.toString
      case startUpConfig: StartUpConfig => startUpConfigSerializer.serialize(startUpConfig)
      case _ => throw new IllegalArgumentException(s"Unsupported type: ${obj.getClass}")
    }
  }

  def deserialize[T](str: String): Option[T]
  
  def startUpConfigSerializer: TypeSerializer[StartUpConfig]
}

object SerializationStrategy {
  def apply(strategy: String): SerializationStrategy = {
    strategy match {
      case "json" => JsonSerializationStrategy()
      case _ => throw new IllegalArgumentException(s"Unknown serialization strategy: $strategy")
    }
  }
}

trait TypeSerializer[T]() {
  def serialize(obj: T): String
  def deserialize(str: String): Option[T]
}