import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.{Terminal, TerminalBuilder}
import org.jline.keymap.{BindingReader, KeyMap}
import org.jline.reader.impl.history.DefaultHistory
import org.jline.utils.{InfoCmp, NonBlockingReader}

import scala.collection.mutable.StringBuilder

object JLineExample extends App {
  // Initialize terminal and reader
  val terminal: Terminal = TerminalBuilder.builder()
    .system(true)
    .build()

  // Create a new history object that we can clear
  val history = new DefaultHistory()

  val reader: LineReader = LineReaderBuilder.builder()
    .terminal(terminal)
    .history(history) // Attach the history
    .variable(LineReader.HISTORY_SIZE, 0) // Disable history size
    .build()

  // Create a binding reader for handling key sequences
  val bindingReader = new BindingReader(terminal.reader())

  // Create and configure key map for command mode
  val keyMap = new KeyMap[Operation]

  // Define possible operations
  sealed trait Operation

  case object Quit extends Operation

  // Basic movement
  case object MoveUp extends Operation

  case object MoveDown extends Operation

  case object MoveLeft extends Operation

  case object MoveRight extends Operation

  // Special keys
  case object Home extends Operation

  case object End extends Operation

  case object PageUp extends Operation

  case object PageDown extends Operation

  case object Insert extends Operation

  case object Delete extends Operation

  case object Backspace extends Operation

  case object Tab extends Operation

  // Function keys
  case object F1 extends Operation

  case object F2 extends Operation

  case object F3 extends Operation

  case object F4 extends Operation

  case object F5 extends Operation

  case object F6 extends Operation

  case object F7 extends Operation

  case object F8 extends Operation

  case object F9 extends Operation

  case object F10 extends Operation

  case object F11 extends Operation

  case object F12 extends Operation

  // Modified keys
  case object CtrlUp extends Operation

  case object CtrlDown extends Operation

  case object CtrlLeft extends Operation

  case object CtrlRight extends Operation

  case object ShiftUp extends Operation

  case object ShiftDown extends Operation

  case object ShiftLeft extends Operation

  case object ShiftRight extends Operation

  case object AltUp extends Operation

  case object AltDown extends Operation

  case object AltLeft extends Operation

  case object AltRight extends Operation

  // Other
  case object SwitchToTextMode extends Operation

  case class CharacterInput(c: Char) extends Operation

  case object Unknown extends Operation

  // Register key bindings
  // Add binding for Ctrl+Q (ASCII 17)
  keyMap.bind(Quit, "\u0011") // Ctrl+Q binding

  // Arrow keys (with both common variants)
  keyMap.bind(MoveUp, "\u001b[A")
  keyMap.bind(MoveDown, "\u001b[B")
  keyMap.bind(MoveLeft, "\u001b[D")
  keyMap.bind(MoveRight, "\u001b[C")
  keyMap.bind(MoveUp, "\u001bOA")
  keyMap.bind(MoveDown, "\u001bOB")
  keyMap.bind(MoveLeft, "\u001bOD")
  keyMap.bind(MoveRight, "\u001bOC")

  // Special keys
  keyMap.bind(Home, "\u001b[H")
  keyMap.bind(End, "\u001b[F")
  keyMap.bind(Home, "\u001b[1~") // Alternative Home
  keyMap.bind(End, "\u001b[4~") // Alternative End
  keyMap.bind(PageUp, "\u001b[5~")
  keyMap.bind(PageDown, "\u001b[6~")
  keyMap.bind(Insert, "\u001b[2~")
  keyMap.bind(Delete, "\u001b[3~")
  keyMap.bind(Backspace, "\u007f")
  keyMap.bind(Tab, "\t")

  // Function keys
  keyMap.bind(F1, "\u001bOP")
  keyMap.bind(F2, "\u001bOQ")
  keyMap.bind(F3, "\u001bOR")
  keyMap.bind(F4, "\u001bOS")
  keyMap.bind(F5, "\u001b[15~")
  keyMap.bind(F6, "\u001b[17~")
  keyMap.bind(F7, "\u001b[18~")
  keyMap.bind(F8, "\u001b[19~")
  keyMap.bind(F9, "\u001b[20~")
  keyMap.bind(F10, "\u001b[21~")
  keyMap.bind(F11, "\u001b[23~")
  keyMap.bind(F12, "\u001b[24~")

  // Control combinations
  keyMap.bind(CtrlUp, "\u001b[1;5A")
  keyMap.bind(CtrlDown, "\u001b[1;5B")
  keyMap.bind(CtrlLeft, "\u001b[1;5D")
  keyMap.bind(CtrlRight, "\u001b[1;5C")

  // Shift combinations
  keyMap.bind(ShiftUp, "\u001b[1;2A")
  keyMap.bind(ShiftDown, "\u001b[1;2B")
  keyMap.bind(ShiftLeft, "\u001b[1;2D")
  keyMap.bind(ShiftRight, "\u001b[1;2C")

  // Alt combinations
  keyMap.bind(AltUp, "\u001b[1;3A")
  keyMap.bind(AltDown, "\u001b[1;3B")
  keyMap.bind(AltLeft, "\u001b[1;3D")
  keyMap.bind(AltRight, "\u001b[1;3C")

  // Other bindings
  keyMap.bind(SwitchToTextMode, "t")

  // Text input mode handler
  def handleTextMode(): String = {
    println("\nEntered text mode. Type your text and press Enter to finish:")
    history.purge() // Clear the history before each input
    val input = reader.readLine("> ")
    history.purge() // Clear the history after input
    input
  }

  // Main input handling loop
  def handleInput(): Unit = {
    println(
      """
        |Command mode:
        |- Arrow keys to move
        |- Function keys F1-F12
        |- Special keys: Home, End, PgUp, PgDn, Ins, Del
        |- Ctrl/Shift/Alt + Arrow keys
        |- 't' for text mode
        |- Ctrl+Q to exit
        |""".stripMargin)

    var running = true
    while (running) {
      val op = try {
        Option(bindingReader.readBinding(keyMap)) match {
          case Some(operation) => operation
          case None => Unknown
        }
      } catch {
        case _: Exception =>
          val char = terminal.reader().read().toChar
          CharacterInput(char)
      }

      handleOperation(op) match {
        case true => // continue
        case false => running = false
      }
    }
  }


  // Operation handler - returns true to continue, false to exit
  def handleOperation(op: Operation): Boolean = op match {
    case Quit => println("Quitting..."); false

    // Basic movement
    case MoveUp => println("↑ Up"); true
    case MoveDown => println("↓ Down"); true
    case MoveLeft => println("← Left"); true
    case MoveRight => println("→ Right"); true

    // Special keys
    case Home => println("⌂ Home"); true
    case End => println("⊣ End"); true
    case PageUp => println("⇞ Page Up"); true
    case PageDown => println("⇟ Page Down"); true
    case Insert => println("⎀ Insert"); true
    case Delete => println("⌦ Delete"); true
    case Backspace => println("⌫ Backspace"); true //isn't working
    case Tab => println("⇥ Tab"); true

    // Function keys
    case F1 => println("F1"); true
    case F2 => println("F2"); true
    case F3 => println("F3"); true
    case F4 => println("F4"); true
    case F5 => println("F5"); true
    case F6 => println("F6"); true
    case F7 => println("F7"); true
    case F8 => println("F8"); true
    case F9 => println("F9"); true
    case F10 => println("F10"); true
    case F11 => println("F11"); true
    case F12 => println("F12"); true

    // Modified keys
    case CtrlUp => println("Ctrl + ↑"); true
    case CtrlDown => println("Ctrl + ↓"); true
    case CtrlLeft => println("Ctrl + ←"); true
    case CtrlRight => println("Ctrl + →"); true
    case ShiftUp => println("Shift + ↑"); true
    case ShiftDown => println("Shift + ↓"); true
    case ShiftLeft => println("Shift + ←"); true
    case ShiftRight => println("Shift + →"); true
    case AltUp => println("Alt + ↑"); true // maybe works outside of the ide?
    case AltDown => println("Alt + ↓"); true
    case AltLeft => println("Alt + ←"); true
    case AltRight => println("Alt + →"); true

    // Other operations
    case SwitchToTextMode =>
      val text = handleTextMode()
      println(s"You entered: $text")
      true
    case Unknown =>
      println("Unknown key sequence")
      true
    case CharacterInput('\u0000') =>
      false // exit on Ctrl+C
    case CharacterInput(c) =>
      println(s"Character pressed: $c")
      true
  }

  try {
    terminal.enterRawMode()
    handleInput()
  } finally {
    terminal.close()
  }
}