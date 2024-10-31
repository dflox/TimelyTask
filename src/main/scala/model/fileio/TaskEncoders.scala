package model.fileio

import com.github.nscala_time.time.Imports.*
import model.settings.DataType
import model.{Deadline, Task}

import java.util.UUID
import scala.collection.immutable.HashSet
import scala.xml.XML

trait TaskEncoders
  extends XmlSerializable[Task]
    with YamlSerializable[Task]
    with Data[Task] {

  // Define DataType for Task
  val dataType: DataType = DataType.TASK

  // Helper for XML Encoding
  def encodeToXml(task: Task): String = {
    <task>
      <name>
        {task.name}
      </name>
      <description>
        {task.description}
      </description>
      <priority>
        {task.priority}
      </priority>
      <tags>
        {task.tags.mkString(",")}
      </tags>
      <deadline>
        {task.deadline}
      </deadline>
      <state>
        {task.state}
      </state>
      <estimatedDuration>
        {task.estimatedDuration}
      </estimatedDuration>
      <dependentOn>
        {task.dependentOn.mkString(",")}
      </dependentOn>
      <reoccurring>
        {task.reoccurring}
      </reoccurring>
      <recurrenceInterval>
        {task.recurrenceInterval}
      </recurrenceInterval>
    </task>.toString()
  }

  // Helper for XML Decoding
  def decodeFromXml(xml: String): Task = {
    val xmlConv = XML.loadString(xml)
    val name = (xmlConv \\ "name").text
    val description = (xmlConv \\ "description").text
    val priority = UUID.fromString((xmlConv \\ "priority").text)
    val tags = HashSet((xmlConv \\ "tags").text.split(",").map(UUID.fromString)*)
    val deadline = Deadline.fromString((xmlConv \\ "deadline").text)
    val state = UUID.fromString((xmlConv \\ "state").text)
    val estimatedDuration = Period.parse((xmlConv \\ "estimatedDuration").text)
    val dependentOn = HashSet((xmlConv \\ "dependentOn").text.split(",").map(UUID.fromString)*)
    val reoccurring = (xmlConv \\ "reoccurring").text.toBoolean
    val recurrenceInterval = Period.parse((xmlConv \\ "recurrenceInterval").text)
    Task(name, description, priority, tags, deadline, state, estimatedDuration, dependentOn, reoccurring, recurrenceInterval)
  }

  // Helper for YAML Encoding
  def encodeToYaml(task: Task): String = {
    s"""
       |name: ${task.name}
       |description: ${task.description}
       |priority: ${task.priority}
       |tags: ${task.tags.mkString(",")}
       |deadline: ${task.deadline}
       |state: ${task.state}
       |estimatedDuration: ${task.estimatedDuration}
       |dependentOn: ${task.dependentOn.mkString(",")}
       |reoccurring: ${task.reoccurring}
       |recurrenceInterval: ${task.recurrenceInterval}
       |""".stripMargin
  }

  // Helper for YAML Decoding
  def decodeFromYaml(yaml: String): Task = {
    val lines = yaml.split("\n").map(_.split(":")(1).trim)
    val name = lines(0)
    val description = lines(1)
    val priority = UUID.fromString(lines(2))
    val tags = HashSet(lines(3).split(",").map(UUID.fromString)*)
    val deadline = Deadline.fromString(lines(4))
    val state = UUID.fromString(lines(5))
    val estimatedDuration = Period.parse(lines(6))
    val dependentOn = HashSet(lines(7).split(",").map(UUID.fromString)*)
    val reoccurring = lines(8).toBoolean
    val recurrenceInterval = Period.parse(lines(9))
    Task(name, description, priority, tags, deadline, state, estimatedDuration, dependentOn, reoccurring, recurrenceInterval)
  }

  // Implement XmlSerializable methods
  override val XmlEncoder: Task => String = encodeToXml
  override val XmlDecoder: String => Task = decodeFromXml

  // Implement YamlSerializable methods
  override val YamlEncoder: Task => String = encodeToYaml
  override val YamlDecoder: String => Task = decodeFromYaml
}
