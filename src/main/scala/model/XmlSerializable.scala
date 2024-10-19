package model

import model.settings.DataType

trait XmlSerializable[T] extends Data[T] {
  val XmlEncoder: T => String
  val XmlDecoder: String => T
}
