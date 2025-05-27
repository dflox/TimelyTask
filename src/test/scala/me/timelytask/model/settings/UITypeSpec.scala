package me.timelytask.model.settings

import me.timelytask.model.settings.DataType
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class UITypeSpec extends AnyWordSpec {
  "The AppType" should {
    "return the correct string representation" in {
      UIType.GUI.toString shouldEqual "gui"
      UIType.TUI.toString shouldEqual "tui"
    }

    "return the correct AppType from a string" in {
      UIType.fromString("gui") shouldEqual UIType.GUI
      UIType.fromString("tui") shouldEqual UIType.TUI
    }
  }
}
