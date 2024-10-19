package model

import model.settings.DataType

import java.awt.Color
import java.util.UUID
import scala.xml.XML

class State(var name: String, var description: String, var color: Color) extends Data[State] with XmlSerializable[State] with YamlSerializable[State] {
  val uuid: UUID = UUID.randomUUID()
  
  val dataType: DataType = DataType.STATE
  
  val XmlEncoder: State => String = state => {
    <state>
      <name>
        {state.name}
      </name>
      <description>
        {state.description}
      </description>
      <color>
        {state.color}
      </color>
    </state>.toString()
  }
  
  val XmlDecoder: String => State = xml => {
    val state = XML.loadString(xml)
    State(
      (state \ "name").text,
      (state \ "description").text,
      Color.decode((state \ "color").text)
    )
  }
  
  val YamlEncoder: State => String = state => {
    s"name: ${state.name}\ndescription: ${state.description}\ncolor: ${state.color}"
  }
  
  val YamlDecoder: String => State = yaml => {
    val name = yaml.split("\n")(0).split(":")(1).trim
    val description = yaml.split("\n")(1).split(":")(1).trim
    val color = Color.decode(yaml.split("\n")(2).split(":")(1).trim)
    State(name, description, color)
  }
    
}
