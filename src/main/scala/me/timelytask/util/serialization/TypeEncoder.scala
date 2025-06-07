package me.timelytask.util.serialization

import io.circe.Json

trait TypeEncoder[T] {
  def apply(obj: T): Json
}
