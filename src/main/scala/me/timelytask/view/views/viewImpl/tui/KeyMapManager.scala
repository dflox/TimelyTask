package me.timelytask.view.views.viewImpl.tui

import me.timelytask.model
import me.timelytask.model.settings
import me.timelytask.model.settings.*
import me.timelytask.model.utility.*
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.events.event.Event
import org.jline.keymap.KeyMap

object KeyMapManager {
  val keyMap: KeyMap[Key] = {
    val keyMap = new KeyMap[Key]()
    // Arrow keys (with both common variants)
    keyMap.bind(Key.MoveUp, "\u001b[A")
    keyMap.bind(Key.MoveDown, "\u001b[B")
    keyMap.bind(Key.MoveLeft, "\u001b[D")
    keyMap.bind(Key.MoveRight, "\u001b[C")
    keyMap.bind(Key.MoveUp, "\u001bOA")
    keyMap.bind(Key.MoveDown, "\u001bOB")
    keyMap.bind(Key.MoveLeft, "\u001bOD")
    keyMap.bind(Key.MoveRight, "\u001bOC")

    // Special keys
    keyMap.bind(Key.Home, "\u001b[H")
    keyMap.bind(Key.End, "\u001b[F")
    keyMap.bind(Key.Home, "\u001b[1~") // Alternative Home
    keyMap.bind(Key.End, "\u001b[4~") // Alternative End
    keyMap.bind(Key.PageUp, "\u001b[5~")
    keyMap.bind(Key.PageDown, "\u001b[6~")
    keyMap.bind(Key.Insert, "\u001b[2~")
    keyMap.bind(Key.Delete, "\u001b[3~")
    keyMap.bind(Key.Backspace, "\u007f")
    keyMap.bind(Key.Tab, "\t")

    // Function keys
    keyMap.bind(Key.F1, "\u001bOP")
    keyMap.bind(Key.F2, "\u001bOQ")
    keyMap.bind(Key.F3, "\u001bOR")
    keyMap.bind(Key.F4, "\u001bOS")
    keyMap.bind(Key.F5, "\u001b[15~")
    keyMap.bind(Key.F6, "\u001b[17~")
    keyMap.bind(Key.F7, "\u001b[18~")
    keyMap.bind(Key.F8, "\u001b[19~")
    keyMap.bind(Key.F9, "\u001b[20~")
    keyMap.bind(Key.F10, "\u001b[21~")
    keyMap.bind(Key.F11, "\u001b[23~")
    keyMap.bind(Key.F12, "\u001b[24~")

    // Control combinations
    keyMap.bind(Key.CtrlUp, "\u001b[1;5A")
    keyMap.bind(Key.CtrlDown, "\u001b[1;5B")
    keyMap.bind(Key.CtrlLeft, "\u001b[1;5D")
    keyMap.bind(Key.CtrlRight, "\u001b[1;5C")

    keyMap.bind(Key.CtrlA, "\u0001")
    keyMap.bind(Key.CtrlB, "\u0002")
    keyMap.bind(Key.CtrlC, "\u0003")
    keyMap.bind(Key.CtrlD, "\u0004")
    keyMap.bind(Key.CtrlE, "\u0005")
    keyMap.bind(Key.CtrlF, "\u0006")
    keyMap.bind(Key.CtrlG, "\u0007")
    keyMap.bind(Key.CtrlH, "\u0008")
    keyMap.bind(Key.CtrlI, "\u0009")
    keyMap.bind(Key.CtrlJ, "\u000a")
    keyMap.bind(Key.CtrlK, "\u000b")
    keyMap.bind(Key.CtrlL, "\u000c")
    keyMap.bind(Key.CtrlM, "\u000d")
    keyMap.bind(Key.CtrlN, "\u000e")
    keyMap.bind(Key.CtrlO, "\u000f")
    keyMap.bind(Key.CtrlP, "\u0010")
    keyMap.bind(Key.CtrlQ, "\u0011")
    keyMap.bind(Key.CtrlR, "\u0012")
    keyMap.bind(Key.CtrlS, "\u0013")
    keyMap.bind(Key.CtrlT, "\u0014")
    keyMap.bind(Key.CtrlU, "\u0015")
    keyMap.bind(Key.CtrlV, "\u0016")
    keyMap.bind(Key.CtrlW, "\u0017")
    keyMap.bind(Key.CtrlX, "\u0018")
    keyMap.bind(Key.CtrlY, "\u0019") // funktioniert oft nicht
    keyMap.bind(Key.CtrlZ, "\u001a") // funktioniert oft nicht
    
    keyMap.bind(Key.CtrlSpace, "\u0000")
    keyMap.bind(Key.CtrlPlus, "\u001b[1;5+")
    keyMap.bind(Key.CtrlMinus, "\u001b[1;5-")
    
    keyMap.bind(Key.CtrlF1, "\u001b[1;5P")
    keyMap.bind(Key.CtrlF2, "\u001b[1;5Q")
    keyMap.bind(Key.CtrlF3, "\u001b[1;5R")
    keyMap.bind(Key.CtrlF4, "\u001b[1;5S")
    keyMap.bind(Key.CtrlF5, "\u001b[15;5~")
    keyMap.bind(Key.CtrlF6, "\u001b[17;5~")
    keyMap.bind(Key.CtrlF7, "\u001b[18;5~")
    keyMap.bind(Key.CtrlF8, "\u001b[19;5~")
    keyMap.bind(Key.CtrlF9, "\u001b[20;5~")
    keyMap.bind(Key.CtrlF10, "\u001b[21;5~")
    keyMap.bind(Key.CtrlF11, "\u001b[23;5~")
    keyMap.bind(Key.CtrlF12, "\u001b[24;5~")

    keyMap.bind(Key.Ctrl1, "\u001b[1;5P")
    keyMap.bind(Key.Ctrl2, "\u001b[1;5Q")
    keyMap.bind(Key.Ctrl3, "\u001b[1;5R")
    keyMap.bind(Key.Ctrl4, "\u001b[1;5S")
    keyMap.bind(Key.Ctrl5, "\u001b[15;5~")
    keyMap.bind(Key.Ctrl6, "\u001b[17;5~")
    keyMap.bind(Key.Ctrl7, "\u001b[18;5~")
    keyMap.bind(Key.Ctrl8, "\u001b[19;5~")
    keyMap.bind(Key.Ctrl9, "\u001b[20;5~")
    keyMap.bind(Key.Ctrl0, "\u001b[21;5~")

    keyMap.bind(Key.Alt1, "\u001b[1;3P")
    keyMap.bind(Key.Alt2, "\u001b[1;3Q")
    keyMap.bind(Key.Alt3, "\u001b[1;3R")
    keyMap.bind(Key.Alt4, "\u001b[1;3S")
    keyMap.bind(Key.Alt5, "\u001b[15;3~")
    keyMap.bind(Key.Alt6, "\u001b[17;3~")
    keyMap.bind(Key.Alt7, "\u001b[18;3~")
    keyMap.bind(Key.Alt8, "\u001b[19;3~")
    keyMap.bind(Key.Alt9, "\u001b[20;3~")
    keyMap.bind(Key.Alt0, "\u001b[21;3~")

    keyMap.bind(Key.ShiftOne, "\u0021")
    keyMap.bind(Key.ShiftTwo, "\u0022")
    keyMap.bind(Key.ShiftThree, "\u00a7")

    // Shift combinations
    keyMap.bind(Key.ShiftUp, "\u001b[1;2A")
    keyMap.bind(Key.ShiftDown, "\u001b[1;2B")
    keyMap.bind(Key.ShiftLeft, "\u001b[1;2D")
    keyMap.bind(Key.ShiftRight, "\u001b[1;2C")

    // Alt combinations
    keyMap.bind(Key.AltUp, "\u001b[1;3A")
    keyMap.bind(Key.AltDown, "\u001b[1;3B")
    keyMap.bind(Key.AltLeft, "\u001b[1;3D")
    keyMap.bind(Key.AltRight, "\u001b[1;3C")

    // Letter keys
    keyMap.bind(Key.A, "a")
    keyMap.bind(Key.B, "b")
    keyMap.bind(Key.C, "c")
    keyMap.bind(Key.D, "d")
    keyMap.bind(Key.E, "e")
    keyMap.bind(Key.F, "f")
    keyMap.bind(Key.G, "g")
    keyMap.bind(Key.H, "h")
    keyMap.bind(Key.I, "i")
    keyMap.bind(Key.J, "j")
    keyMap.bind(Key.K, "k")
    keyMap.bind(Key.L, "l")
    keyMap.bind(Key.M, "m")
    keyMap.bind(Key.N, "n")
    keyMap.bind(Key.O, "o")
    keyMap.bind(Key.P, "p")
    keyMap.bind(Key.Q, "q")
    keyMap.bind(Key.R, "r")
    keyMap.bind(Key.S, "s")
    keyMap.bind(Key.T, "t")
    keyMap.bind(Key.U, "u")
    keyMap.bind(Key.V, "v")
    keyMap.bind(Key.W, "w")
    keyMap.bind(Key.X, "x")
    keyMap.bind(Key.Y, "y")
    keyMap.bind(Key.Z, "z")

    // Number keys
    keyMap.bind(Key.Num0, "0")
    keyMap.bind(Key.Num1, "1")
    keyMap.bind(Key.Num2, "2")
    keyMap.bind(Key.Num3, "3")
    keyMap.bind(Key.Num4, "4")
    keyMap.bind(Key.Num5, "5")
    keyMap.bind(Key.Num6, "6")
    keyMap.bind(Key.Num7, "7")
    keyMap.bind(Key.Num8, "8")
    keyMap.bind(Key.Num9, "9")

    // Other bindings
    keyMap.bind(Key.Space, " ")
    keyMap.bind(Key.Enter, "\n")
    keyMap.bind(Key.Dot, ".")
    keyMap.bind(Key.Comma, ",")
    keyMap.bind(Key.Colon, ":")
    keyMap.bind(Key.Plus, "+")
    keyMap.bind(Key.Minus, "-")
    keyMap.bind(Key.Semicolon, ";")
    keyMap.bind(Key.Slash, "/")
    keyMap.bind(Key.Backslash, "\\")

    keyMap
  }
}