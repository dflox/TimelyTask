package model.fileio

import model.State
import model.settings.DataType

import java.awt.Color
import scala.xml.XML

trait StateEncoders
    extends XmlSerializable[State]
    with YamlSerializable[State]
    with Data[State] {

  // Define DataType for State
  val dataType: DataType = DataType.STATE

  // Helper for XML Encoding
  def encodeToXml(state: State): String = {
    <state>
      <name>{state.name}</name>
      <description>{state.description}</description>
      <color>{state.color.getRGB}</color>
    </state>.toString()
  }

  // Helper for XML Decoding
  def decodeFromXml(xml: String): State = {
    val stateXml = XML.loadString(xml)
    val name = (stateXml \ "name").text
    val description = (stateXml \ "description").text
    val color = Color.decode((stateXml \ "color").text)
    State(name, description, color)
  }

  // Helper for YAML Encoding
  def encodeToYaml(state: State): String = {
    s"""
       |name: ${state.name}
       |description: ${state.description}
       |color: ${state.color.getRGB}
       |""".stripMargin
  }

  // Helper for YAML Decoding
  def decodeFromYaml(yaml: String): State = {
    val lines = getLines(yaml)
    val name = lines(0)
    val description = lines(1)
    val color = Color.decode(lines(2))
    State(name, description, color)
  }

  // Implement XmlSerializable methods
  override val XmlEncoder: State => String = encodeToXml
  override val XmlDecoder: String => State = decodeFromXml

  // Implement YamlSerializable methods
  override val YamlEncoder: State => String = encodeToYaml
  override val YamlDecoder: String => State = decodeFromYaml
}
