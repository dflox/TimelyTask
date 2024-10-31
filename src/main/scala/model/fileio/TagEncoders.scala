package model.fileio

import model.Tag
import model.settings.DataType

import scala.xml.XML

// Trait for handling serialization of Tag class
trait TagEncoders extends XmlSerializable[Tag] with YamlSerializable[Tag] with Data[Tag] {
  val dataType: DataType = DataType.TAG

  // XML Encoder for Tag
  def encodeToXml(tag: Tag): String = {
    <tag>
      <name>{tag.name}</name>
      <description>{tag.description.getOrElse("")}</description>
    </tag>.toString()
  }

  // XML Decoder for Tag
  def decodeFromXml(xml: String): Tag = {
    val tagXml = XML.loadString(xml)
    val name = (tagXml \ "name").text
    val description = (tagXml \ "description").text
    Tag(name, if (description.isEmpty) None else Some(description))
  }

  // YAML Encoder for Tag
  def encodeToYaml(tag: Tag): String = {
    s"name: ${tag.name}\ndescription: ${tag.description.getOrElse("")}"
  }

  // YAML Decoder for Tag
  def decodeFromYaml(yaml: String): Tag = {
    val lines = getLines(yaml)
    val name = lines(0)
    val description = lines(1)
    Tag(name, if (description.isEmpty) None else Some(description))
  }

  // Implement XmlSerializable and YamlSerializable methods
  override val XmlEncoder: Tag => String = encodeToXml
  override val XmlDecoder: String => Tag = decodeFromXml

  override val YamlEncoder: Tag => String = encodeToYaml
  override val YamlDecoder: String => Tag = decodeFromYaml
}