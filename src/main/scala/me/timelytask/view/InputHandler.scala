package me.timelytask.view

import me.timelytask.controller.keyMapManager
import me.timelytask.model.settings.*
import me.timelytask.model.utility.Keyboard


class InputHandler {
  def handleInput(key: Keyboard): Boolean = {
    val action: Action = keyMapManager.getActiveActionKeymap.getOrElse(key, keyMapManager
      .getGlobalActionKeymap.getOrElse(key, NoAction))
    action.call
  }
}