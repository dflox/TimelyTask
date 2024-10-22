package model

import com.github.nscala_time.time.Imports.*
import model.fileio.*
import model.settings.DataType

import java.util.UUID
import scala.collection.immutable.HashSet

case class Task(name: String, description: String,
                priority: UUID, tags: HashSet[UUID] = new HashSet[UUID](), deadline: Deadline,
                status: UUID, estimatedDuration: Period, dependentOn: HashSet[UUID] = new HashSet[UUID](),
                reoccurring: Boolean, recurrenceInterval: Period)
  extends TaskEncoders {

  val uuid: UUID = UUID.randomUUID()
  val realDuration: Option[Period] = None
  val completionDate: Option[DateTime] = None
}

object Task extends TaskEncoders {
  implicit val yamlSerializable: YamlSerializable[Task] = new YamlSerializable[Task] {
    override val YamlEncoder: Task => String = encodeToYaml
    override val YamlDecoder: String => Task = decodeFromYaml
  }
  
  implicit val xmlSerializable: XmlSerializable[Task] = new XmlSerializable[Task] {
    override val dataType: DataType = DataType.TASK
    override val XmlEncoder: Task => String = encodeToXml
    override val XmlDecoder: String => Task = decodeFromXml
  }
    
}