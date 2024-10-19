package model

import com.github.nscala_time.time.Imports.*
import model.settings.DataType
import model.settings.DataType.*

import java.util.UUID
import scala.collection.immutable.HashSet
import scala.xml.XML

case class Task(name: String, description: String,
                priority: UUID, tags: HashSet[UUID] = new HashSet[UUID](), deadline: Deadline,
                status: UUID, estimatedDuration: Period, dependentOn: HashSet[UUID] = new HashSet[UUID](),
                reoccurring: Boolean, recurrenceInterval: Period)
  extends Data[Task] with YamlSerializable[Task] with XmlSerializable[Task] {

  val uuid: UUID = UUID.randomUUID()
  val realDuration: Option[Period] = None
  val completionDate: Option[DateTime] = None

  val dataType: DataType = DataType.TASK

  val XmlEncoder: Task => String = task => {
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
        {task.tags}
      </tags>
      <deadline>
        {task.deadline}
      </deadline>
      <status>
        {task.status}
      </status>
      <estimatedDuration>
        {task.estimatedDuration}
      </estimatedDuration>
      <dependentOn>
        {task.dependentOn}
      </dependentOn>
      <reoccurring>
        {task.reoccurring}
      </reoccurring>
      <recurrenceInterval>
        {task.recurrenceInterval}
      </recurrenceInterval>
    </task>.toString()
  }

  val XmlDecoder: String => Task = xml => {
    val xmlConv = XML.loadString(xml)
    val name = (xmlConv \\ "name").text
    val description = (xmlConv \\ "description").text
    val priority = UUID.fromString((xmlConv \\ "priority").text)
    val tags = HashSet((xmlConv \\ "tags").text.split(",").map(UUID.fromString): _*)
    val deadline = Deadline.fromPrintString((xmlConv \\ "deadline").text)
    val status = UUID.fromString((xmlConv \\ "status").text)
    val estimatedDuration = Period.parse((xmlConv \\ "estimatedDuration").text)
    val dependentOn = HashSet((xmlConv \\ "dependentOn").text.split(",").map(UUID.fromString): _*)
    val reoccurring = (xmlConv \\ "reoccurring").text.toBoolean
    val recurrenceInterval = Period.parse((xmlConv \\ "recurrenceInterval").text)
    Task(name, description, priority, tags, deadline, status, estimatedDuration, dependentOn, reoccurring, recurrenceInterval)
  }

  val YamlEncoder: Task => String = task => {
    s"name: ${task.name}\ndescription: ${task.description}\npriority: ${task.priority}\ntags: ${task.tags}\ndeadline: ${task.deadline}\nstatus: ${task.status}\nestimatedDuration: ${task.estimatedDuration}\ndependentOn: ${task.dependentOn}\nreoccurring: ${task.reoccurring}\nrecurrenceInterval: ${task.recurrenceInterval}"
  }

  val YamlDecoder: String => Task = yaml => {
    val name = yaml.split("\n")(0).split(":")(1).trim
    val description = yaml.split("\n")(1).split(":")(1).trim
    val priority = UUID.fromString(yaml.split("\n")(2).split(":")(1).trim)
    val tags = HashSet(yaml.split("\n")(3).split(":")(1).trim.split(",").map(UUID.fromString): _*)
    val deadline = Deadline.fromPrintString(yaml.split("\n")(4).split(":")(1).trim)
    val status = UUID.fromString(yaml.split("\n")(5).split(":")(1).trim)
    val estimatedDuration = Period.parse(yaml.split("\n")(6).split(":")(1).trim)
    val dependentOn = HashSet(yaml.split("\n")(7).split(":")(1).trim.split(",").map(UUID.fromString): _*)
    val reoccurring = yaml.split("\n")(8).split(":")(1).trim.toBoolean
    val recurrenceInterval = Period.parse(yaml.split("\n")(9).split(":")(1).trim)
    Task(name, description, priority, tags, deadline, status, estimatedDuration, dependentOn, reoccurring, recurrenceInterval)
  }
}