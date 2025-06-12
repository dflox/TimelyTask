package me.timelytask.util.serialization.decoder

import io.circe.{Decoder, Json}
import me.timelytask.core.StartUpConfig
import me.timelytask.util.serialization.TypeDecoder

given TypeDecoder[StartUpConfig] with {
  def apply(json: Json): Option[StartUpConfig] = {
    json.as[StartUpConfig](Decoder.startUpConfig) match {
      case Right(startUpConfig) => Some(startUpConfig)
      case Left(_) => None
    }
  }
}