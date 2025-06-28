package me.timelytask.view.views.viewImpl.tui

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

class KeyMapManagerSpec extends AnyWordSpec with Matchers with MockitoSugar {
  "KeyMapManager" should {
    "create bindings correctly" in {
      KeyMapManager.keyMap.getBoundKeys should not be empty
    }
  }
}