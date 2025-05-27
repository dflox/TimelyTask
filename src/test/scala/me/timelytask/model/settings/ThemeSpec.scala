package me.timelytask.model.settings

import me.timelytask.model.settings.Theme
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ThemeSpec extends AnyWordSpec {

  "The Theme" should {
    "print the correct theme" in {
      Theme.LIGHT.toString should be("light")
      Theme.DARK.toString should be("dark")
    }
    "create the correct theme from a string" in {
      Theme.fromString("light") should be(Theme.LIGHT)
      Theme.fromString("dark") should be(Theme.DARK)
    }
  }

}
