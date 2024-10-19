package model

import model.settings.DataType

trait YamlSerializable[T] {
  val YamlEncoder: T => String
  val YamlDecoder: String => T
}
