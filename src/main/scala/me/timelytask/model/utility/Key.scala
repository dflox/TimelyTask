package me.timelytask.model.utility

sealed trait Key

object Key {

  def fromString(s: String): Key = s match {
    case "Up"          => MoveUp
    case "Down"        => MoveDown
    case "Left"        => MoveLeft
    case "Right"       => MoveRight
    case "Home"        => Home
    case "End"         => End
    case "PageUp"      => PageUp
    case "PageDown"    => PageDown
    case "Insert"      => Insert
    case "Delete"      => Delete
    case "Backspace"   => Backspace
    case "Tab"         => Tab
    case "Space"       => Space
    case "Enter"       => Enter
    case "Dot"         => Dot
    case "Comma"       => Comma
    case "Colon"       => Colon
    case "Semicolon"   => Semicolon
    case "Plus"        => Plus
    case "Minus"       => Minus
    case "Slash"       => Slash
    case "Backslash"   => Backslash
    case "F1"          => F1
    case "F2"          => F2
    case "F3"          => F3
    case "F4"          => F4
    case "F5"          => F5
    case "F6"          => F6
    case "F7"          => F7
    case "F8"          => F8
    case "F9"          => F9
    case "F10"         => F10
    case "F11"         => F11
    case "F12"         => F12
    case "Ctrl+Up"     => CtrlUp
    case "Ctrl+Down"   => CtrlDown
    case "Ctrl+Left"   => CtrlLeft
    case "Ctrl+Right"  => CtrlRight
    case "Shift+Up"    => ShiftUp
    case "Shift+Down"  => ShiftDown
    case "Shift+Left"  => ShiftLeft
    case "Shift+Right" => ShiftRight
    case "Alt+Up"      => AltUp
    case "Alt+Down"    => AltDown
    case "Alt+Left"    => AltLeft
    case "Alt+Right"   => AltRight
    case "Ctrl+A"      => CtrlA
    case "Ctrl+B"      => CtrlB
    case "Ctrl+C"      => CtrlC
    case "Ctrl+D"      => CtrlD
    case "Ctrl+E"      => CtrlE
    case "Ctrl+F"      => CtrlF
    case "Ctrl+G"      => CtrlG
    case "Ctrl+H"      => CtrlH
    case "Ctrl+I"      => CtrlI
    case "Ctrl+J"      => CtrlJ
    case "Ctrl+K"      => CtrlK
    case "Ctrl+L"      => CtrlL
    case "Ctrl+M"      => CtrlM
    case "Ctrl+N"      => CtrlN
    case "Ctrl+O"      => CtrlO
    case "Ctrl+P"      => CtrlP
    case "Ctrl+Q"      => CtrlQ
    case "Ctrl+R"      => CtrlR
    case "Ctrl+S"      => CtrlS
    case "Ctrl+T"      => CtrlT
    case "Ctrl+U"      => CtrlU
    case "Ctrl+V"      => CtrlV
    case "Ctrl+W"      => CtrlW
    case "Ctrl+X"      => CtrlX
    case "Ctrl+Y"      => CtrlY
    case "Ctrl+Z"      => CtrlZ
    case "Ctrl+Plus"   => CtrlPlus
    case "Ctrl+Minus"  => CtrlMinus
    case "Ctrl+Space"  => CtrlSpace
    case "Ctrl+F1"     => CtrlF1
    case "Ctrl+F2"     => CtrlF2
    case "Ctrl+F3"     => CtrlF3
    case "Ctrl+F4"     => CtrlF4
    case "Ctrl+F5"     => CtrlF5
    case "Ctrl+F6"     => CtrlF6
    case "Ctrl+F7"     => CtrlF7
    case "Ctrl+F8"     => CtrlF8
    case "Ctrl+F9"     => CtrlF9
    case "Ctrl+F10"    => CtrlF10
    case "Ctrl+F11"    => CtrlF11
    case "Ctrl+F12"    => CtrlF12
    case "A"           => A
    case "B"           => B
    case "C"           => C
    case "D"           => D
    case "E"           => E
    case "F"           => F
    case "G"           => G
    case "H"           => H
    case "I"           => I
    case "J"           => J
    case "K"           => K
    case "L"           => L
    case "M"           => M
    case "N"           => N
    case "O"           => O
    case "P"           => P
    case "Q"           => Q
    case "R"           => R
    case "S"           => S
    case "T"           => T
    case "U"           => U
    case "V"           => V
    case "W"           => W
    case "X"           => X
    case "Y"           => Y
    case "Z"           => Z
    case "Num0"        => Num0
    case "Num1"        => Num1
    case "Num2"        => Num2
    case "Num3"        => Num3
    case "Num4"        => Num4
    case "Num5"        => Num5
    case "Num6"        => Num6
    case "Num7"        => Num7
    case "Num8"        => Num8
    case "Num9"        => Num9
    case "Unknown"     => Unknown
    case _             => Unknown // Default to Unknown if no match found
  }

// Basic movement
  case object MoveUp extends Key

  case object MoveDown extends Key

  case object MoveLeft extends Key

  case object MoveRight extends Key

// Special keys
  case object Home extends Key

  case object End extends Key

  case object PageUp extends Key

  case object PageDown extends Key

  case object Insert extends Key

  case object Delete extends Key

  case object Backspace extends Key

  case object Tab extends Key

// Function keys
  case object F1 extends Key

  case object F2 extends Key

  case object F3 extends Key

  case object F4 extends Key

  case object F5 extends Key

  case object F6 extends Key

  case object F7 extends Key

  case object F8 extends Key

  case object F9 extends Key

  case object F10 extends Key

  case object F11 extends Key

  case object F12 extends Key

// Modified keys
  case object CtrlUp extends Key

  case object CtrlDown extends Key

  case object CtrlLeft extends Key

  case object CtrlRight extends Key

  case object ShiftUp extends Key

  case object ShiftDown extends Key

  case object ShiftLeft extends Key

  case object ShiftRight extends Key

  case object AltUp extends Key

  case object AltDown extends Key

  case object AltLeft extends Key

  case object AltRight extends Key

  case object CtrlA extends Key

  case object CtrlB extends Key

  case object CtrlC extends Key

  case object CtrlD extends Key

  case object CtrlE extends Key

  case object CtrlF extends Key

  case object CtrlG extends Key

  case object CtrlH extends Key

  case object CtrlI extends Key

  case object CtrlJ extends Key

  case object CtrlK extends Key

  case object CtrlL extends Key

  case object CtrlM extends Key

  case object CtrlN extends Key

  case object CtrlO extends Key

  case object CtrlP extends Key

  case object CtrlQ extends Key

  case object CtrlR extends Key

  case object CtrlS extends Key

  case object CtrlT extends Key

  case object CtrlU extends Key

  case object CtrlV extends Key

  case object CtrlW extends Key

  case object CtrlX extends Key

  case object CtrlY extends Key

  case object CtrlZ extends Key

  case object CtrlPlus extends Key

  case object CtrlMinus extends Key

  case object CtrlSpace extends Key

  case object CtrlF1 extends Key
  case object CtrlF2 extends Key
  case object CtrlF3 extends Key
  case object CtrlF4 extends Key
  case object CtrlF5 extends Key
  case object CtrlF6 extends Key
  case object CtrlF7 extends Key
  case object CtrlF8 extends Key
  case object CtrlF9 extends Key
  case object CtrlF10 extends Key
  case object CtrlF11 extends Key
  case object CtrlF12 extends Key

// Letter keys
  case object A extends Key

  case object B extends Key

  case object C extends Key

  case object D extends Key

  case object E extends Key

  case object F extends Key

  case object G extends Key

  case object H extends Key

  case object I extends Key

  case object J extends Key

  case object K extends Key

  case object L extends Key

  case object M extends Key

  case object N extends Key

  case object O extends Key

  case object P extends Key

  case object Q extends Key

  case object R extends Key

  case object S extends Key

  case object T extends Key

  case object U extends Key

  case object V extends Key

  case object W extends Key

  case object X extends Key

  case object Y extends Key

  case object Z extends Key

// Number keys

  case object Num0 extends Key

  case object Num1 extends Key

  case object Num2 extends Key

  case object Num3 extends Key

  case object Num4 extends Key

  case object Num5 extends Key

  case object Num6 extends Key

  case object Num7 extends Key

  case object Num8 extends Key

  case object Num9 extends Key

// Other

  case object Space extends Key

  case object Enter extends Key

  case object Dot extends Key

  case object Comma extends Key

  case object Colon extends Key

  case object Semicolon extends Key

  case object Plus extends Key

  case object Minus extends Key

  case object Slash extends Key

  case object Backslash extends Key

  case object Unknown extends Key
}
