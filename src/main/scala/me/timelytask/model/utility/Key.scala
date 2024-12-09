package me.timelytask.model.utility

sealed trait Key

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