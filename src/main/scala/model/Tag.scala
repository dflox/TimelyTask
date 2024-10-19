package model

import java.util.UUID
import scala.xml.XML
import model.settings.DataType

case class Tag(name: String, description: Option[String]) extends YamlSerializable[Tag] with XmlSerializable[Tag] with Data[Tag]{
  val uuid: UUID = UUID.randomUUID()
  val dataType: DataType = DataType.TAG

  val XmlEncoder: Tag => String = tag => {
    <tag>
      <name>
        {tag.name}
      </name>
      <description>
        {tag.description}
      </description>
    </tag>
  }.toString

  val XmlDecoder: String => Tag = xml => {
    val tag = XML.loadString(xml)
    Tag(
      (tag \ "name").text,
      if ((tag \ "description").text.isEmpty) None else Some((tag \ "description").text)
    )
  }

  val YamlEncoder: Tag => String = tag => {
    s"name: ${tag.name}\ndescription: ${tag.description.getOrElse("")}"
  }

  val YamlDecoder: String => Tag = yaml => {
    val name = yaml.split("\n")(0).split(":")(1).trim
    val description = yaml.split("\n")(1).split(":")(1).trim
    Tag(name, if (description.isEmpty) None else Some(description))
  }
}
