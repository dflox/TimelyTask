package me.timelytask.view

import me.timelytask.model.settings.*
import me.timelytask.model.utility.Key
import me.timelytask.view.events.Event
import me.timelytask.view.keymaps.keyMapManager


class KeyInputHandler() {
  def handleInput(key: Key): Boolean = {
    val action: Event = keyMapManager.getActiveActionKeymap.getOrElse(key, keyMapManager
      .getGlobalActionKeymap.getOrElse(key, NoEvent))
    action.call
  }
}