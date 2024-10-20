package model.fileio

import model.Priority

import java.awt.Color
import scala.xml.XML
import model.settings.DataType

trait PriorityEncoders
    extends XmlSerializable[Priority]
    with YamlSerializable[Priority]
    with Data[Priority] {

  val dataType: DataType = DataType.PRIORITY

  // XML Encoder for Priority
  def encodeToXml(priority: Priority): String = {
    <priority>
      <name>{priority.name}</name>
      <description>{priority.description}</description>
      <rank>{priority.rank}</rank>
      <color>{priority.color.getRGB}</color>
      <daysPreDeadline>{priority.daysPreDeadline}</daysPreDeadline>
      <postponable>{priority.postponable}</postponable>
    </priority>.toString()
  }

  // XML Decoder for Priority
  def decodeFromXml(xml: String): Priority = {
    val priorityXml = XML.loadString(xml)
    val name = (priorityXml \ "name").text
    val description = (priorityXml \ "description").text
    val rank = (priorityXml \ "rank").text.toInt
    val color = Color.decode((priorityXml \ "color").text)
    val daysPreDeadline = (priorityXml \ "daysPreDeadline").text.toInt
    val postponable = (priorityXml \ "postponable").text.toBoolean
    Priority(name, description, rank, color, daysPreDeadline, postponable)
  }

  // YAML Encoder for Priority
  def encodeToYaml(priority: Priority): String = {
    s"""
       |name: ${priority.name}
       |description: ${priority.description}
       |rank: ${priority.rank}
       |color: ${priority.color.getRGB}
       |daysPreDeadline: ${priority.daysPreDeadline}
       |postponable: ${priority.postponable}
       |""".stripMargin
  }

  // YAML Decoder for Priority
  def decodeFromYaml(yaml: String): Priority = {
    val lines = getLines(yaml)
    val name = lines(0)
    val description = lines(1)
    val rank = lines(2).toInt
    val color = Color.decode(lines(3))
    val daysPreDeadline = lines(4).toInt
    val postponable = lines(5).toBoolean
    Priority(name, description, rank, color, daysPreDeadline, postponable)
  }

  // Implement XmlSerializable and YamlSerializable methods
  override val XmlEncoder: Priority => String = encodeToXml
  override val XmlDecoder: String => Priority = decodeFromXml

  override val YamlEncoder: Priority => String = encodeToYaml
  override val YamlDecoder: String => Priority = decodeFromYaml
}