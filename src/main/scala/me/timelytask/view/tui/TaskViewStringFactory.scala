package me.timelytask.view.tui

import me.timelytask.model.Task
import me.timelytask.model.settings.TASKEdit
import me.timelytask.view.tui.TuiUtils.{alignTop, columnSpacer, createLine, createSpace}
import me.timelytask.view.viewmodel.*
import me.timelytask.view.viewmodel.elemts.{FocusElementGrid, InputField, OptionInputField}

object TaskViewStringFactory extends StringFactory {
  
  val longestPropertyLength: Int = {
    List[String](Task.descDescription, Task.descPriority, Task.descTags, Task.descDeadline, Task
      .descScheduleDate, Task.descState, Task.descTedDuration, Task.descDependentOn, Task
      .descReoccurring, Task.descRecurrenceInterval)
      .map(_.length)
      .max
  }
  
  override def buildString(viewModel: ViewModel[TASKEdit]): String = {
    buildString(viewModel, ModelTUI.default)
  }

  override def buildString(viewModel: ViewModel[TASKEdit], TUIModel: ModelTUI): String = {
    val taskViewModel: TaskEditViewModel = viewModel.asInstanceOf[TaskEditViewModel]
    import TUIModel.*
    import taskViewModel.*

    val builder = new StringBuilder()
    builder.append(taskStringBuilder(task, terminalWidth, terminalHeight, getFocusElementGrid.get, 
      longestPropertyLength))
    builder.toString()
  }

    private def taskStringBuilder(task: Task, width: Int, terminalHeight: Int, focusElementGrid: FocusElementGrid,
                                  columnSize: Int): String = {
      val builder = new StringBuilder()
      builder.append(task.name).append("\n")
      builder.append(createLine(width)).append("\n")

      val separator: String = " | "
      var totalLines = 0

      for (focusable <- focusElementGrid.elementsList) {
        if focusable.isEmpty then builder.append(createLine(width)).append("\n")
        else {
          val inputField: Option[InputField[?] | OptionInputField[?]] = focusable.get match
            case 
            case None =>

          focusable.get match
            case Some(value) =>
            case None =>

          val name = focusable.name
          val formattedName = columnSpacer(s"$name:", columnSize, "l")
          val valueStr = value.toString
          val maxLineLength = width - columnSize - separator.length
          var remainingValue = valueStr
          var valueLines = 0

          builder.append(formattedName).append(separator)

          while (remainingValue.length > maxLineLength) {
            val splitIndex = remainingValue.lastIndexWhere(_.isWhitespace, maxLineLength)
            val (line, rest) = if (splitIndex > 0) {
              remainingValue.splitAt(splitIndex)
            } else {
              remainingValue.splitAt(maxLineLength)
            }
            builder.append(line.trim).append("\n")
            builder.append(createSpace(columnSize)).append(separator)
            remainingValue = rest.trim
            valueLines += 1
          }

          builder.append(remainingValue).append("\n")
          valueLines += 1
          totalLines += 1 + valueLines
        }
      }

      builder.append(alignTop(terminalHeight, totalLines))
      builder.toString()
    }
}
