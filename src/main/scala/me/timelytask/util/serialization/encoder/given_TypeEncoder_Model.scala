package me.timelytask.util.serialization.encoder

import io.circe.{Encoder, Json}
import me.timelytask.model.Model
import me.timelytask.util.serialization.TypeEncoder

given TypeEncoder[Model] with {
  def apply(obj: Model): Json = {
    Encoder.model(obj)
  }
}