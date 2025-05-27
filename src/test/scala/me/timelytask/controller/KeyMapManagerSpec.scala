package me.timelytask.controller

import me.timelytask.model.settings.*
import me.timelytask.model.utility.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import me.timelytask.model.settings.activeViewPublisher
import me.timelytask.view.tui.KeyMapManager

class KeyMapManagerSpec extends AnyWordSpec {
  "The KeyMapManager" should {
    "set global action keymap" in {
      val keyMapManager = KeyMapManager
      keyMapManager.setGlobalActionKeymap(Map(CtrlQ -> Exit))
      keyMapManager.getGlobalActionKeymap should be(Map(CtrlQ -> Exit))
    }

    "set action keymap" in {
      val keyMapManager = KeyMapManager
      keyMapManager.setKeymap(ViewType.KANBAN, Map(MoveRight -> NextDay))
      keyMapManager.getActiveActionKeymap should not be Map(MoveRight -> NextDay)
      activeViewPublisher.update(ViewType.KANBAN)
      keyMapManager.getActiveActionKeymap should be(Map(MoveRight -> NextDay))
    }
  }
}