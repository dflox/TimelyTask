package me.timelytask.util.serialization.encoder

import io.circe.{Encoder, Json}
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.Model
import me.timelytask.model.settings.{UIType, ViewType}
import me.timelytask.util.serialization.TypeEncoder

given TypeEncoder[StartUpConfig] with {
  def apply(obj: StartUpConfig): Json = {
    Encoder.startUpConfig(obj)
  }
}