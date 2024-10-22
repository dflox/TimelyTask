package model

import model.fileio._
import model.settings.DataType

import java.awt.Color
import java.util.UUID

class State(var name: String, var description: String, var color: Color) extends StateEncoders {
  val uuid: UUID = UUID.randomUUID()
}

object State extends StateEncoders {
  
  implicit val yamlSerializable: YamlSerializable[State] = new YamlSerializable[State] {
    override val YamlEncoder: State => String = encodeToYaml
    override val YamlDecoder: String => State = decodeFromYaml
  }

  implicit val xmlSerializable: XmlSerializable[State] = new XmlSerializable[State] {
    override val dataType: DataType = DataType.STATE
    override val XmlEncoder: State => String = encodeToXml
    override val XmlDecoder: String => State = decodeFromXml
  }
}
