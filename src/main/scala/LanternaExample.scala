import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.screen.{Screen, TerminalScreen}
import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.input.KeyType

object LanternaExample {
  def main(args: Array[String]): Unit = {
    // Create a terminal factory
    val terminalFactory = new DefaultTerminalFactory()

    // Create and start the terminal screen
    val terminal = terminalFactory.createTerminal()
    val screen: Screen = new TerminalScreen(terminal)
    screen.startScreen()

    // Draw some text on the screen
    screen.setCharacter(10, 10, TextCharacter.fromCharacter('H')(0))
    screen.setCharacter(11, 10, TextCharacter.fromCharacter('e')(0))
    screen.setCharacter(12, 10, TextCharacter.fromCharacter('l')(0))
    screen.setCharacter(13, 10, TextCharacter.fromCharacter('l')(0))
    screen.setCharacter(14, 10, TextCharacter.fromCharacter('o')(0))
    screen.refresh()

    // Wait for a key press
    var running = true
    while (running) {
      val key = screen.readInput()
      if (key.getKeyType == KeyType.Escape) {
        running = false // Exit the loop on 'Esc' key press
      }
    }

    // Stop the screen and close the terminal
    screen.stopScreen()
  }
}
