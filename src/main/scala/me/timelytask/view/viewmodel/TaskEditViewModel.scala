package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.{DateTime, LocalTime, Period}
import me.timelytask.model.builder.TaskBuilder
import me.timelytask.model.settings.*
import me.timelytask.model.state.TaskState
import me.timelytask.model.{Deadline, Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.dialogmodel.{DialogModel, InputDialogModel, OptionDialogModel}
import me.timelytask.view.viewmodel.elemts.{FocusElementGrid, Focusable, InputField, OptionInputField}

import java.util.UUID
import scala.collection.immutable.HashSet
import scala.util.{Failure, Success, Try}

case class TaskEditViewModel(taskID: UUID, task: Task = Task(),
                             lastView: Option[ViewType], isNewTask: Boolean = false,
                             protected var focusElementGrid: Option[FocusElementGrid] = None,
                             dialogModel: Option[DialogModel[?]] = None,
                             modelPublisher: Publisher[Model])
  extends ViewModel[TASKEdit](modelPublisher) {

  focusElementGridInit()

  private def focusElementGridInit(): Unit = {
    Try[Task] {
      model().get.tasks.find(_.uuid.equals(taskID)).get
    } match {
      case Failure(_) => return
      case Success(t) =>
        val elements = createElements(t, model().get)
        focusElementGrid = Some(FocusElementGrid(elements, elements(0)(0)))
    }
  }

  private def createElements(task: Task, model: Model): Vector[Vector[Option[Focusable[?]]]] =
    Vector(Vector(
      Some(InputField[String](Task.descDescription, Some(task.description), (s: String) => s)),

      Some(OptionInputField[UUID](Task.descPriority, model.priorities.toList.map(_.uuid),
        (uuid: UUID) =>
          model.priorities.find(_.uuid.equals(uuid)) match
            case Some(p) => p.name
            case None => "Priority undefined",
        task.priority.toList)),

      Some(OptionInputField[UUID](Task.descTags, model.tags.toList.map(_.uuid), (uuid: UUID) =>
        model.tags.find(_.uuid.equals(uuid)) match
          case Some(t) => t.name
          case None => "Tag undefined",
        task.tags.toList, None, None)),

      Some(InputField[DateTime](Task.descDeadline, Some(task.deadline.date), (dt: DateTime) =>
        dt.toString("dd/MM/yyyy HH:mm"))),

      Some(InputField[DateTime](Task.descScheduleDate, Some(task.scheduleDate), (dt: DateTime) =>
        dt.toString("dd/MM/yyyy HH:mm"))),

      Some(OptionInputField[UUID](Task.descState, model.states.toList.map(_.uuid), (uuid: UUID) =>
        model.states.find(_.uuid.equals(uuid)) match
          case Some(s) => s.name
          case None => "State undefined",
        task.state.toList)),

      Some(InputField[Period](Task.descTedDuration, Some(task.tedDuration), (p: Period) =>
        p.getHours + "h " + p.getMinutes + "m")),

      Some(OptionInputField[UUID](Task.descDependentOn, model.tasks.map(_.uuid), (uuid: UUID) =>
        model.tasks.find(_.uuid.equals(uuid)) match
          case Some(t) => t.name
          case None => "Task undefined",
        task.dependentOn.toList, Some(0), None)),

      Some(InputField[Boolean](Task.descReoccurring, Some(task.reoccurring), (b: Boolean) =>
        if (b) "Yes" else "No")),

      Some(InputField[Period](Task.descRecurrenceInterval, Some(task.recurrenceInterval),
        (p: Period) =>
          p.getWeeks + "w " + p.getDays + "d " + p.getHours + "h " + p.getMinutes + "m"))
    ))

  def getFocusElementGrid: Option[FocusElementGrid] = focusElementGrid

  def interact[RenderType](currentView: Option[RenderType],
                           optionDialogInputGetter: (Option[OptionDialogModel[?]], 
                             Option[RenderType]) => 
                             Option[?],
                           inputDialogInputGetter: (Option[InputDialogModel[?]], Option[RenderType])
                             => 
                             Option[?])
  : Option[TaskEditViewModel] = {

    def findCorrectInputGetter(focusedElement: Option[Focusable[?]]):
    Option[TaskEditViewModel] = {
      focusedElement match
        case Some(inputField: InputField[?]) =>
          getInputFromInputDialog(inputField)
        case Some(optionInputField: OptionInputField[?]) =>
          getInputFromOptionDialog(optionInputField)
        case _ => None
    }

    def getInputFromInputDialog(inputField: InputField[?]): Option[TaskEditViewModel] = {
      copyTask(getUpdatedTask(inputField.description,
        inputDialogInputGetter(Some(inputField.dialogModel), currentView)))
    }

    def getInputFromOptionDialog(optionInputField: OptionInputField[?]): Option[TaskEditViewModel] = {
      copyTask(getUpdatedTask(optionInputField.description,
        optionDialogInputGetter(Some(optionInputField.dialogModel), currentView)))
    }

    def copyTask(task: Option[Task]): Option[TaskEditViewModel] = {
      task match
        case Some(t) => Some(this.copy(task = t))
        case None => None
    }
    
    if focusElementGrid.isEmpty then return None 
    findCorrectInputGetter(focusElementGrid.get.getFocusedElement)
  }

  def getUpdatedTask[T](property: String, valueOpt: Option[T]): Option[Task] = {
    if valueOpt.isEmpty then return None
    val value = valueOpt.get
    val taskBuilder = TaskBuilder(task)
    property match {
      case Task.descDescription => value match {
        case s: String => Some(taskBuilder.setDescription(s).build())
        case _ => None
      }
      case Task.descPriority => value match {
        case u: UUID => Some(taskBuilder.setPriority(u).build())
        case _ => None
      }
      case Task.descTags => value match {
        case hs: HashSet[UUID] => Some(taskBuilder.setTags(hs).build())
        case _ => None
      }
      case Task.descDeadline => value match {
        case d: Deadline => Some(taskBuilder.setDeadline(d).build())
        case _ => None
      }
      case Task.descScheduleDate => value match {
        case dt: DateTime => Some(taskBuilder.setScheduleDate(dt).build())
        case _ => None
      }
      case Task.descState => value match {
        case ts: UUID => Some(taskBuilder.setState(Some(ts)).build())
        case _ => None
      }
      case Task.descTedDuration => value match {
        case p: Period => Some(taskBuilder.setTedDuration(p).build())
        case _ => None
      }
      case Task.descDependentOn => value match {
        case hs: HashSet[UUID] => Some(taskBuilder.setDependentOn(hs).build())
        case _ => None
      }
      case Task.descReoccurring => value match {
        case b: Boolean => Some(taskBuilder.setReoccurring(b).build())
        case _ => None
      }
      case Task.descRecurrenceInterval => value match {
        case p: Period => Some(taskBuilder.setRecurrenceInterval(p).build())
        case _ => None
      }
      case _ => None
    }
  }
}