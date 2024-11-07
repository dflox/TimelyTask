package me.timelytask.model.settings

import me.timelytask.model.settings.DataType
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class AppTypeSpec extends AnyWordSpec {
  "The AppType" should {
    "return the correct string representation" in {
      AppType.GUI.toString shouldEqual "gui"
      AppType.TUI.toString shouldEqual "tui"
    }

    "return the correct AppType from a string" in {
      AppType.fromString("gui") shouldEqual AppType.GUI
      AppType.fromString("tui") shouldEqual AppType.TUI
    }
  }
}
