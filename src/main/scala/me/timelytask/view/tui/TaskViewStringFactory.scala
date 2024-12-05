package me.timelytask.view.tui

import me.timelytask.model.Task
import me.timelytask.view.tui.TuiUtils.{alignTop, columnSpacer, createLine, createSpace}
import me.timelytask.view.viewmodel.*

object TaskViewStringFactory extends StringFactory {
  override def buildString(viewModel: ViewModel): String = {
    buildString(viewModel, ModelTUI.default)
  }

  override def buildString(viewModel: ViewModel, TUIModel: ModelTUI): String = {
    val taskViewModel: TaskModel = viewModel.asInstanceOf[TaskModel]
    import TUIModel.*
    import taskViewModel.*

    val builder = new StringBuilder()
    builder.append(taskStringBuilder(task, terminalWidth, terminalHeight, properties, longestProperty.length))
    builder.toString()
  }

    private def taskStringBuilder(task: Task, width: Int, terminalHeight: Int, properties: List[(String, Any)], columnSize: Int): String = {
      val builder = new StringBuilder()
      builder.append(task.name).append("\n")
      builder.append(createLine(width)).append("\n")

      val separator: String = " | "
      var totalLines = 0

      for ((name, value) <- properties) {
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

      builder.append(alignTop(terminalHeight, totalLines))
      builder.toString()
    }
}
