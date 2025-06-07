package model.settings

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

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
