package view

import model.*
import model.settings.ViewType
import org.jline.keymap.KeyMap

class KeyMapManager extends ActiveViewObserver {
  private var keymaps: Map[ViewType, KeyMap[Action]] = KeyMapManager.defaultKeymaps
  private var globalKeymap: KeyMap[Action] = KeyMapManager.globalKeymap
  private var activeView: ViewType = ViewType.CALENDAR

  def onActiveViewChange(viewType: ViewType): Unit = {
    activeView = viewType
  }
  
  def getActiveKeymap: KeyMap[Action] = {
    keymaps(activeView)
  }

  def getGlobalKeymap: KeyMap[Action] = {
    globalKeymap
  }

  def setGlobalKeymap(keymap: KeyMap[Action]): Unit = {
    globalKeymap = keymap
  }

  def setKeymap(name: ViewType, keymap: KeyMap[Action]): Unit = {
    keymaps = keymaps + (name -> keymap)
  }
}

private object KeyMapManager {
  private val defaultKeymaps: Map[ViewType, KeyMap[Action]] = Map(
    ViewType.CALENDAR -> {
      val keymap = new KeyMap[Action]()
      keymap.bind(NextDay, "\u001B[C")
      keymap.bind(PreviousDay, "\u001B[D")
      keymap.bind(NextWeek, "\u001B[1;5C")
      keymap.bind(PreviousWeek, "\u001B[1;5D")
      keymap.bind(GoToToday, "t")
      keymap
    },
    ViewType.TABLE -> new KeyMap[Action](),
    ViewType.TASK -> new KeyMap[Action](),
    ViewType.KANBAN -> new KeyMap[Action](),
    ViewType.SETTINGS -> new KeyMap[Action]()
  )
  private val globalKeymap: KeyMap[Action] = new KeyMap[Action]()
}