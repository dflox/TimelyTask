package me.timelytask.model.utility

sealed trait Keyboard

// Basic movement
case object MoveUp extends Keyboard

case object MoveDown extends Keyboard

case object MoveLeft extends Keyboard

case object MoveRight extends Keyboard

// Special keys
case object Home extends Keyboard

case object End extends Keyboard

case object PageUp extends Keyboard

case object PageDown extends Keyboard

case object Insert extends Keyboard

case object Delete extends Keyboard

case object Backspace extends Keyboard

case object Tab extends Keyboard

// Function keys
case object F1 extends Keyboard

case object F2 extends Keyboard

case object F3 extends Keyboard

case object F4 extends Keyboard

case object F5 extends Keyboard

case object F6 extends Keyboard

case object F7 extends Keyboard

case object F8 extends Keyboard

case object F9 extends Keyboard

case object F10 extends Keyboard

case object F11 extends Keyboard

case object F12 extends Keyboard

// Modified keys
case object CtrlUp extends Keyboard

case object CtrlDown extends Keyboard

case object CtrlLeft extends Keyboard

case object CtrlRight extends Keyboard

case object ShiftUp extends Keyboard

case object ShiftDown extends Keyboard

case object ShiftLeft extends Keyboard

case object ShiftRight extends Keyboard

case object AltUp extends Keyboard

case object AltDown extends Keyboard

case object AltLeft extends Keyboard

case object AltRight extends Keyboard

case object CtrlA extends Keyboard

case object CtrlB extends Keyboard

case object CtrlC extends Keyboard

case object CtrlD extends Keyboard

case object CtrlE extends Keyboard

case object CtrlF extends Keyboard

case object CtrlG extends Keyboard

case object CtrlH extends Keyboard

case object CtrlI extends Keyboard

case object CtrlJ extends Keyboard

case object CtrlK extends Keyboard

case object CtrlL extends Keyboard

case object CtrlM extends Keyboard

case object CtrlN extends Keyboard

case object CtrlO extends Keyboard

case object CtrlP extends Keyboard

case object CtrlQ extends Keyboard

case object CtrlR extends Keyboard

case object CtrlS extends Keyboard

case object CtrlT extends Keyboard

case object CtrlU extends Keyboard

case object CtrlV extends Keyboard

case object CtrlW extends Keyboard

case object CtrlX extends Keyboard

case object CtrlY extends Keyboard

case object CtrlZ extends Keyboard

// Letter keys
case object A extends Keyboard

case object B extends Keyboard

case object C extends Keyboard

case object D extends Keyboard

case object E extends Keyboard

case object F extends Keyboard

case object G extends Keyboard

case object H extends Keyboard

case object I extends Keyboard

case object J extends Keyboard

case object K extends Keyboard

case object L extends Keyboard

case object M extends Keyboard

case object N extends Keyboard

case object O extends Keyboard

case object P extends Keyboard

case object Q extends Keyboard

case object R extends Keyboard

case object S extends Keyboard

case object T extends Keyboard

case object U extends Keyboard

case object V extends Keyboard

case object W extends Keyboard

case object X extends Keyboard

case object Y extends Keyboard

case object Z extends Keyboard

// Number keys

case object Num0 extends Keyboard

case object Num1 extends Keyboard

case object Num2 extends Keyboard

case object Num3 extends Keyboard

case object Num4 extends Keyboard

case object Num5 extends Keyboard

case object Num6 extends Keyboard

case object Num7 extends Keyboard

case object Num8 extends Keyboard

case object Num9 extends Keyboard

// Other

case object Space extends Keyboard

case object Enter extends Keyboard

case object Dot extends Keyboard

case object Comma extends Keyboard

case object Colon extends Keyboard

case object Semicolon extends Keyboard

case object Plus extends Keyboard

case object Minus extends Keyboard

case object Slash extends Keyboard

case object Backslash extends Keyboard

case object Unknown extends Keyboard