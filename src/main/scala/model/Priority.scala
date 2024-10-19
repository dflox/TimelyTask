package model

import java.awt.Color
import java.util.UUID
import scala.xml.XML
import model.settings.DataType

case class Priority(name: String, description: String, rank: Int, color: Color,
               daysPreDeadline: Int, postponable: Boolean) extends YamlSerializable[Priority] with XmlSerializable[Priority] with Data[Priority] {
  val uuid: UUID = UUID.randomUUID()
  val dataType: DataType = DataType.PRIORITY

  val XmlEncoder: Priority => String = priority => {
    <priority>
      <name>
        {priority.name}
      </name>
      <description>
        {priority.description}
      </description>
      <rank>
        {priority.rank}
      </rank>
      <color>
        {priority.color}
      </color>
      <daysPreDeadline>
        {priority.daysPreDeadline}
      </daysPreDeadline>
      <postponable>
        {priority.postponable}
      </postponable>
    </priority>
  }.toString

  val XmlDecoder: String => Priority = xml => {
    val priority = XML.loadString(xml)
    Priority(
      (priority \ "name").text,
      (priority \ "description").text,
      (priority \ "rank").text.toInt,
      Color.decode((priority \ "color").text),
      (priority \ "daysPreDeadline").text.toInt,
      (priority \ "postponable").text.toBoolean
    )
  }

  val YamlEncoder: Priority => String = priority => {
    s"name: ${priority.name}\ndescription: ${priority.description}\nrank: ${priority.rank}\ncolor: ${priority.color}\ndaysPreDeadline: ${priority.daysPreDeadline}\npostponable: ${priority.postponable}"
  }

  val YamlDecoder: String => Priority = yaml => {
    val name = yaml.split("\n")(0).split(":")(1).trim
    val description = yaml.split("\n")(1).split(":")(1).trim
    val rank = yaml.split("\n")(2).split(":")(1).trim.toInt
    val color = Color.decode(yaml.split("\n")(3).split(":")(1).trim)
    val daysPreDeadline = yaml.split("\n")(4).split(":")(1).trim.toInt
    val postponable = yaml.split("\n")(5).split(":")(1).trim.toBoolean
    Priority(name, description, rank, color, daysPreDeadline, postponable)
  }
}