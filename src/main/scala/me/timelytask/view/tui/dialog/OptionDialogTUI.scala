package me.timelytask.view.tui.dialog

import me.timelytask.view.tui.TuiUtils.{createLine, cutText}
import me.timelytask.view.viewmodel.dialogmodel.{DialogModel, OptionDialogModel}
import org.jline.terminal.Terminal

class OptionDialogTUI[T](override val dialogModel: Option[OptionDialogModel[T]],
                         override val currentView: Option[String],
                         override val terminal: Terminal)
  extends TUIDialog[T] {

  override def apply(): Option[T] = {
    if dialogModel.isEmpty | currentView.isEmpty then return None

    if (dialogModel.get.options.length > 36) {
      throw new IllegalArgumentException("OptionDialogTUI only supports up to 36 options currently")
    }

    val options = dialogModel.get.options
    val terminalWidth = terminal.getWidth
    val dialogString = createDialogString(options, dialogModel.get.displayFunc, terminalWidth)
    val viewWithDialog = overlapString(currentView.get, dialogString)
    terminal.writer().println(viewWithDialog)

    val keyMap = createCustomKeyMap(options)
    var userInput = -1
    while (!keyMap.contains(userInput)) {
      userInput = charToNum(terminal.reader().read().toChar)
    }
    Some(keyMap(userInput))
  }

  private def createDialogString(options: List[T], displayFunc: T => String, terminalWidth: Int)
  : String = {
    val stringBuilder = new StringBuilder()
    stringBuilder.append(createLine(terminalWidth) + "\n")

    stringBuilder.append(cutText(s"Please select an option (0-${
      numToCharString(options.length - 1)
    })" + ":", terminalWidth) + "\n")
    for (i <- options.indices) {
      stringBuilder.append(cutText(s"(${numToCharString(i)}) " + displayFunc(options(i)),
        terminalWidth) + "\n")
    }

    stringBuilder.append(createLine(terminalWidth))
    stringBuilder.toString()
  }

  private def numToCharString(num: Int): String = {
    if num >= 0 & num <= 9 then num.toString
    if num >= 0 & num <= 36 then (num - 10 + 97).toChar.toString
    else throw new IllegalArgumentException(
      "numToCharString only supports up to 36 options currently")
  }

  private def charToNum(char: Char): Int = {
    if char.isDigit then char.asDigit
    else if char.isLetter then char.toLower.toInt - 97 + 10
         else -1
  }

  private def createCustomKeyMap(options: List[T]): Map[Int, T] = {
    options.indices.map(i => (i, options(i))).toMap
  }

}
