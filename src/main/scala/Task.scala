import com.github.nscala_time.time.Imports._
import java.util.UUID
import scala.collection.immutable.HashSet

case class Task (name: String, description: String,
                 priority: Priority, deadline: Deadline, estimatedDuration: Period,
                 dependentOn: HashSet[Task] = new HashSet[Task](), reoccurring: Boolean, recurrenceInterval: Period,
                 status: Status) {
  val uuid: UUID = UUID.randomUUID()
  val realDuration: Option[Period] = None
  val completionDate: Option[DateTime] = None


}
