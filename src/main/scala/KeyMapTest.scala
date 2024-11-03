import org.jline.terminal.{Terminal, TerminalBuilder}
import org.jline.keymap.KeyMap
import org.jline.utils.InfoCmp.Capability

object KeyMapTest {
  def main(args: Array[String]): Unit = {
    // Create a terminal
    val terminal: Terminal = TerminalBuilder.builder()
      .system(true) // Use the system terminal
      .jansi(true) // Enable ANSI support for colors, if needed
      .build()

    // Enter raw mode to process input immediately
    terminal.enterRawMode()

    // Create a key map
    val keyMap = new KeyMap[String]

    // Add key bindings
    keyMap.bind("action1", "a")          // Bind 'a' key to "action1"
    keyMap.bind("action2", "b")          // Bind 'b' key to "action2"
    keyMap.bind("action3", KeyMap.key(terminal, Capability.key_left))   // Bind right arrow key to "action3"
    keyMap.bind("exit", KeyMap.ctrl('x')) // Bind Ctrl + X to "exit"

    // Main loop to read inputs
    var continue = true
    val reader = terminal.reader() // Get the terminal reader

    while (continue) {
      // Read a single character (this doesn't wait for Enter)
      // This will block until a key is pressed
      val input = reader.read()
      
      // Convert input to String to use in getBound
      val action = keyMap.getBound(input.toChar.toString) // Convert char to String

      // Handle the actions based on key pressed
      action match {
        case "action1" =>
          terminal.writer().println("You pressed 'a'")
        case "action2" =>
          terminal.writer().println("You pressed 'b'")
        case "action3" =>
          terminal.writer().println("You pressed 'arrow left'")
        case "exit" =>
          terminal.writer().println("Exiting...")
          continue = false
        case _ =>
          terminal.writer().println(s"Unknown key: ${input.toChar}")
      }

      terminal.flush() // Flush the output to make sure it's printed immediately
    }

    terminal.close() // Close the terminal when done
  }
}
