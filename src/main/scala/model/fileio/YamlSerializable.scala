package model.fileio

trait YamlSerializable[T] {
  val YamlEncoder: T => String
  val YamlDecoder: String => T
  val getLines: String => Array[String] = yaml => yaml.split("\n").map(_.split(":")(1).trim)
}
