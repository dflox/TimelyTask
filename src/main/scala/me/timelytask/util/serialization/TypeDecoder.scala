package me.timelytask.util.serialization

import io.circe.Json

trait TypeDecoder[T] {
  def apply(json: Json): Option[T]
}
