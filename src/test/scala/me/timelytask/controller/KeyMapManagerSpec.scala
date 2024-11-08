package me.timelytask.controller

import me.timelytask.model.settings.*
import me.timelytask.model.utility.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class KeyMapManagerSpec extends AnyWordSpec {
  "The KeyMapManager" should {
    "set global action keymap" in {
      val keyMapManager = new KeyMapManager
      keyMapManager.setGlobalActionKeymap(Map(F12 -> Exit))
      keyMapManager.getGlobalActionKeymap should be(Map(F12 -> Exit))
    }
    "set action keymap" in {
      val keyMapManager = new KeyMapManager
      keyMapManager.setKeymap(ViewType.KANBAN, Map(MoveRight -> NextDay))
      keyMapManager.onChange(ViewType.KANBAN)
      keyMapManager.getActiveActionKeymap should be(Map(MoveRight -> NextDay))
    }
  }

}
