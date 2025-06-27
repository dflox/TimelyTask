package me.timelytask.util.serialization.decoder

import io.circe.{Decoder, Json}
import me.timelytask.model.Model
import me.timelytask.util.serialization.TypeDecoder

given TypeDecoder[Model] with {
  def apply(json: Json): Option[Model] = {
    json.as[Model](Decoder.model) match {
      case Right(model) => Some(model)
      case Left(_) => None
    }
  }
}