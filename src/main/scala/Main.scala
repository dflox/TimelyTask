package com.googlecode.lanterna.gui2

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import java.io.IOException
import java.util.regex.Pattern


object Calculator {
  @throws[IOException]
  def main(args: Array[String]): Unit = {
    // Setup terminal and screen layers
    val terminal = new DefaultTerminalFactory().createTerminal
    val screen = new TerminalScreen(terminal)
    screen.startScreen()
    // Create panel to hold components
    val panel = new Panel
    panel.setLayoutManager(new GridLayout(2))
    val lblOutput = new Label("")
    panel.addComponent(new Label("Num 1"))
    val txtNum1 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel)
    panel.addComponent(new Label("Num 2"))
    val txtNum2 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel)
    panel.addComponent(new EmptySpace(new TerminalSize(0, 0)))
    new Button("Add!", new Runnable() {
      override def run(): Unit = {
        val num1 = txtNum1.getText.toInt
        val num2 = txtNum2.getText.toInt
        lblOutput.setText(Integer.toString(num1 + num2))
      }
    }).addTo(panel)
    panel.addComponent(new EmptySpace(new TerminalSize(0, 0)))
    panel.addComponent(lblOutput)
    // Create window to hold the panel
    val window = new BasicWindow
    window.setComponent(panel)
    // Create gui and start gui
    val gui = new MultiWindowTextGUI(screen, new DefaultWindowManager, new EmptySpace(TextColor.ANSI.BLUE))
    gui.addWindowAndWait(window)
  }
}