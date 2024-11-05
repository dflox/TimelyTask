package controller

import model.*
import model.settings.ViewType
import org.jline.keymap.KeyMap
import model.utility.*

class KeyMapManager extends ActiveViewObserver {
  private var actionKeyMaps: Map[ViewType, Map[Keyboard, Action]] = KeyMapManager.defaultActionKeymaps
  private var globalActionKeymap: Map[Keyboard, Action] = KeyMapManager.globalKeymap
  private var activeView: ViewType = ViewType.CALENDAR

  def onActiveViewChange(viewType: ViewType): Unit = {
    activeView = viewType
  }

  def getActiveActionKeymap: Map[Keyboard, Action] = {
    actionKeyMaps(activeView)
  }

  def getGlobalActionKeymap: Map[Keyboard, Action] = {
    globalActionKeymap
  }

  def setGlobalActionKeymap(actionKeyMap: Map[Keyboard, Action]): Unit = {
    globalActionKeymap = actionKeyMap
  }

  def setKeymap(name: ViewType, actionKeyMap: Map[Keyboard, Action]): Unit = {
    actionKeyMaps = actionKeyMaps + (name -> actionKeyMap)
  }
}

object KeyMapManager {

  private val globalKeymap: Map[Keyboard, Action] = Map()
  
  private val defaultActionKeymaps: Map[ViewType, Map[Keyboard, Action]] = Map(
    ViewType.CALENDAR -> {
      Map(
        MoveRight -> NextDay,
        MoveLeft -> PreviousDay,
        CtrlRight -> NextWeek,
        CtrlLeft -> PreviousWeek,
        utility.T -> GoToToday
      )
    },
    ViewType.TABLE -> Map(),
    ViewType.TASK -> Map(),
    ViewType.KANBAN -> Map(),
    ViewType.SETTINGS -> Map()
  )

  val keyMap: KeyMap[Keyboard] = {
    val keyMap = new KeyMap[Keyboard]()
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
    
    // Letter keys
    keyMap.bind(A, "a")
    keyMap.bind(B, "b")
    keyMap.bind(C, "c")
    keyMap.bind(D, "d")
    keyMap.bind(E, "e")
    keyMap.bind(F, "f")
    keyMap.bind(G, "g")
    keyMap.bind(H, "h")
    keyMap.bind(I, "i")
    keyMap.bind(J, "j")
    keyMap.bind(K, "k")
    keyMap.bind(L, "l")
    keyMap.bind(M, "m")
    keyMap.bind(N, "n")
    keyMap.bind(O, "o")
    keyMap.bind(P, "p")
    keyMap.bind(Q, "q")
    keyMap.bind(R, "r")
    keyMap.bind(S, "s")
    keyMap.bind(T, "t")
    keyMap.bind(U, "u")
    keyMap.bind(V, "v")
    keyMap.bind(W, "w")
    keyMap.bind(X, "x")
    keyMap.bind(Y, "y")
    keyMap.bind(Z, "z")
    
    // Number keys
    keyMap.bind(Num0, "0")
    keyMap.bind(Num1, "1")
    keyMap.bind(Num2, "2")
    keyMap.bind(Num3, "3")
    keyMap.bind(Num4, "4")
    keyMap.bind(Num5, "5")
    keyMap.bind(Num6, "6")
    keyMap.bind(Num7, "7")
    keyMap.bind(Num8, "8")
    keyMap.bind(Num9, "9")
    
    // Other bindings
    keyMap.bind(Space, " ")
    keyMap.bind(Enter, "\n")
    keyMap.bind(Dot, ".")
    keyMap.bind(Comma, ",")
    keyMap.bind(Colon, ":")
    keyMap.bind(Semicolon, ";")
    keyMap.bind(Slash, "/")
    keyMap.bind(Backslash, "\\")   
    
    keyMap
  }
}