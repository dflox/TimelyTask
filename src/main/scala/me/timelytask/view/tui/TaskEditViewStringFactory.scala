package me.timelytask.view.tui

import me.timelytask.controller.ThemeManager.{getTerminalBgColor, getTerminalColor}
import me.timelytask.model.Task
import me.timelytask.model.settings.TASKEdit
import me.timelytask.model.settings.ThemeSystem.ColorSupport.Terminal.colored
import me.timelytask.view.tui.TuiUtils.{alignTop, columnSpacer, createLine, createSpace}
import me.timelytask.view.viewmodel.*
import me.timelytask.view.viewmodel.elemts.{FocusElementGrid, InputField, OptionInputField}

object TaskEditViewStringFactory extends StringFactory[TASKEdit, TaskEditViewModel] {

  val longestPropertyLength: Int = {
    List[String](Task.descDescription, Task.descPriority, Task.descTags, Task.descDeadline, Task
      .descScheduleDate, Task.descState, Task.descTedDuration, Task.descDependentOn, Task
      .descReoccurring, Task.descRecurrenceInterval)
      .map(_.length)
      .max
  }

  override def buildString(taskViewModel: TaskEditViewModel): String = {
    buildString(taskViewModel, ModelTUI.default)
  }

  override def buildString(taskEditViewModel: TaskEditViewModel, tuiModel: ModelTUI): String = {
    import tuiModel.*
    import taskEditViewModel.*

    val builder = new StringBuilder()
    if getFocusElementGrid.isEmpty then {
      builder.append(createLine(terminalWidth)).append("\n")
      builder.append(f"!!! Task ${task.name} is not defined. Please try again./n !!!")
      builder.append(createLine(terminalWidth)).append("\n")
      builder.append(alignTop(terminalHeight, 3))
    } else {
      builder.append(taskStringBuilder(task, terminalWidth, terminalHeight, getFocusElementGrid.get,
        longestPropertyLength))
    }
    builder.toString()
  }

  private def taskStringBuilder(task: Task, width: Int, terminalHeight: Int, 
                                focusElementGrid: FocusElementGrid,
                                columnSize: Int): String = {
    val builder = new StringBuilder()
    builder.append(task.name).append("\n")
    builder.append(createLine(width)).append("\n")

    val separator: String = " | "
    var totalLines = 0


    for (focusable <- focusElementGrid.elementsList) {
      if focusable.isEmpty then builder.append(createLine(width)).append("\n")
      else {
        var description: String = ""
        var value: String = ""
        focusable.get match
          case inputField: InputField[?] => {
            description = inputField.description
            if inputField.defaultInput.isDefined then value = inputField.displayFunc(inputField
              .defaultInput.get)
          }
          case optionInputField: OptionInputField[?] => {
            description = optionInputField.description
            if optionInputField.default.nonEmpty 
            then value = optionInputField.default.map(optionInputField.displayFunc).mkString (", ")
          }

        val (str, valueLines) = buildEntry(description, value, width, columnSize,
          focusable.equals(focusElementGrid.getFocusedElement.get), getTerminalBgColor(
            _.background2))
        totalLines += 1 + valueLines
      }
    }

    builder.append(alignTop(terminalHeight, totalLines))
    builder.toString()
  }

  def buildEntry(description: String, value: String, width: Int, columnSize: Int,
                 isFocused: Boolean, backgroundColorFocused: String): (str: String, valueLines:
    Int) = {
    val separator: String = " | "
    val maxLineLength = width - columnSize - separator.length
    val formattedName = colored(columnSpacer(s"$description:", columnSize, "l"),
      backgroundColorFocused)
    val builder = new StringBuilder()
    var remainingValue = value
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
    (builder.toString(), valueLines)
  }
}
