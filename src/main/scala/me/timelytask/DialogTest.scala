//package me.timelytask
//
//import me.timelytask.view.views.viewImpl.tui.dialog.DialogFactoryTUI
//import me.timelytask.view.viewmodel.dialogmodel.{ConfirmDialogModel, OptionDialogModel, 
// InputDialogModel}
//import org.jline.terminal.{Terminal, TerminalBuilder}
//
//object DialogTest extends App {
//
//  val terminal: Terminal = TerminalBuilder.builder()
//    .system(true)
//    .build()
//
//  val dialogFactory = new DialogFactoryTUI(terminal)
//
//  // create a multiline string without /r
//  val exampleViewString =
//    """
//      |This is an example view string
//      |It is a multiline string
//      |It is used to test the dialog
//      |It is not that small
//      |
//      |
//      |
//      | wow so many lines
//      |""".stripMargin
//      .replaceAll("\r", "")
//
//  val inputDialogModel = InputDialogModel("Please enter your text:", exampleViewString)
//  val dialog = dialogFactory.createDialog(inputDialogModel)
//
//  val userInput = dialog.getUserInput
//  terminal.writer().println(s"User input: $userInput")
//
//  val confirmDialogModel = ConfirmDialogModel("Findest du den Tag heute schön?", 
//  exampleViewString)
//  val dialog2 = dialogFactory.createDialog(confirmDialogModel)
//
//  val userInput2 = dialog2.getUserInput
//  terminal.writer().println(s"User input: $userInput2")
//
//  val list = List("Option 1", "Option 2", "Option 3")
//  val optionDialogModel = OptionDialogModel[String](list, exampleViewString)
//  val dialog3 = dialogFactory.createDialog(optionDialogModel)
//
//  val userInput3 = dialog3.getUserInput
//  terminal.writer().println(s"User input: $userInput3")
//}
