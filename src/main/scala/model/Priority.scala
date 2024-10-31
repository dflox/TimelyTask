package model

import model.fileio.*
import model.settings.DataType

import java.awt.Color
import java.util.UUID

case class Priority(name: String, description: String, rank: Int, color: Color,
                    daysPreDeadline: Int, postponable: Boolean)
  extends PriorityEncoders {
  val uuid: UUID = UUID.randomUUID()
}

object Priority extends PriorityEncoders {
  implicit val yamlSerializable: YamlSerializable[Priority] = new YamlSerializable[Priority] {
    override val YamlEncoder: Priority => String = encodeToYaml
    override val YamlDecoder: String => Priority = decodeFromYaml
  }

  implicit val xmlSerializable: XmlSerializable[Priority] = new XmlSerializable[Priority] {
    override val dataType: DataType = DataType.PRIORITY
    override val XmlEncoder: Priority => String = encodeToXml
    override val XmlDecoder: String => Priority = decodeFromXml
  }
}