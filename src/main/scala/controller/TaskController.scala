package controller

import com.github.nscala_time.time.Imports.*
import io.circe.generic.auto.*
import view.*
import view.model.settings.FileType.{JSON, XML}
import view.model.settings.StartView.TABLE
import view.model.settings.Theme.DARK
import view.model.settings.{DataType, FileType, StartView, Theme}
import model.{Config, State, *}

import java.awt.Color
import java.util.UUID
import scala.collection.immutable.{HashMap, HashSet}

class TaskController(view: View) extends Controller {

  val tag: HashSet[UUID] = HashSet(new Tag(name = "test", None).uuid)
  val testTask1: Task = new Task("Test", "Test", new Priority(name = "test", description = "test", rank = 1, color = Color.BLACK, daysPreDeadline = 4, postponable = false).uuid, tag, new Deadline(date = DateTime.now(), None, None), new State(name = "test", description = "test", color = Color.BLACK).uuid, Period.days(1), new HashSet[UUID](), false, Period.days(1))
  val testTask2: Task = new Task(name = "Test", description = "test", priority = new Priority(name = "test", description = "test", rank = 1, color = Color.BLACK, daysPreDeadline = 4, postponable = false).uuid, deadline = new Deadline(date = DateTime.now(), None, None), state = new State(name = "test", description = "test", color = Color.BLACK).uuid, estimatedDuration = Period.days(1), reoccurring = false, recurrenceInterval = Period.days(1))
  var tasks: List[Task] = List()
  var tags: HashMap[UUID, Tag] = HashMap()
  var priorities: HashMap[UUID, Priority] = HashMap()
  var states: HashMap[UUID, State] = HashMap()
  val config: Config = TaskController.getConfig

  def run(): Unit = {
    println(view.update(tasks));
    val dead = testTask1.deadline.toString()
  }
}

object TaskController {
  val defaultConfigFolderPath: String = "./"

  def getConfig: Config = {
    val config: Config = new Config(defaultStartView = TABLE, defaultDataFileType = FileType.JSON, defaultTheme = Theme.DARK)
    config
  }
}