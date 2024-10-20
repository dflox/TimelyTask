package model

import model.fileio._

import java.util.UUID
import model.settings.DataType

case class Tag(name: String, description: Option[String]) extends TagEncoders {
  val uuid: UUID = UUID.randomUUID()
}

object Tag extends TagEncoders {
  implicit val yamlSerializable: YamlSerializable[Tag] = new YamlSerializable[Tag] {
    override val YamlEncoder: Tag => String = encodeToYaml
    override val YamlDecoder: String => Tag = decodeFromYaml
  }

  implicit val xmlSerializable: XmlSerializable[Tag] = new XmlSerializable[Tag] {
    override val dataType: DataType = DataType.TAG
    override val XmlEncoder: Tag => String = encodeToXml
    override val XmlDecoder: String => Tag = decodeFromXml
  }
}
