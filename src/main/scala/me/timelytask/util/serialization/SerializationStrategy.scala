package me.timelytask.util.serialization

import me.timelytask.util.serialization.serializer.*

import scala.util.Try

trait SerializationStrategy {
  val fileExtension: String
  def serialize[T](obj: T)(using typeEncoder: TypeEncoder[T]): String
  def deserialize[T](str: String)(using typeDecoder: TypeDecoder[T]): Option[T]
}

object SerializationStrategy {
  /**
   * Factory method to create a SerializationStrategy based on the provided strategy name.
   * @param serializationType the name of the serialization strategy, e.g., "json" or "yaml".
   * @return an instance of SerializationStrategy corresponding to the specified strategy.
   *         Throws IllegalArgumentException if the strategy is unknown.
   */
  def apply(serializationType: String): SerializationStrategy = {
    serializationType match {
      case "json" => JsonSerializationStrategy()
      case "yaml" => YamlSerializationStrategy()
      case "xml" => YamlSerializationStrategy()
      case _ => throw new IllegalArgumentException(s"Unknown serialization strategy: $serializationType")
    }
  }
  
  def tryApply(serializationType: String): Option[SerializationStrategy] = {
    Try {
      apply(serializationType)
    }.toOption
  }
}