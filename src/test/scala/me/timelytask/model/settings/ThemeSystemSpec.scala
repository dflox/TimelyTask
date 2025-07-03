package me.timelytask.model.settings

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

/**
 * A test suite for ThemeSystem that is adapted to bypass JavaFX dependencies.
 *
 * NOTE: Any method that directly calls `scalafx.scene.paint.Color.web()`
 * cannot be unit tested without a running JavaFX toolkit. This test suite
 * therefore omits tests for `hexToColor` and `hexToTerminal256` as they
 * are untestable in a pure unit test environment.
 */
class ThemeSystemSpec extends AnyWordSpec with Matchers with TableDrivenPropertyChecks {

  "ThemeSystem.Theme" should {

    "be created with correct default hex values" in {
      // This test is pure and does not depend on JavaFX. It can remain.
      val defaultTheme = ThemeSystem.Theme()

      // Test a representative sample of default values.
      defaultTheme.background0 should be("#17171c")
      defaultTheme.text1 should be("#4BA6FB")
      defaultTheme.accent0 should be("#BA274A")
      defaultTheme.error should be("#f7768e")
    }
  }

  "ThemeSystem.ColorSupport.Terminal" should {

    "colored should correctly wrap text with color and reset codes" in {
      val text = "Hello"
      val color = ThemeSystem.ColorSupport.Terminal.BOLD
      val expected = s"${ThemeSystem.ColorSupport.Terminal.BOLD}$text${ThemeSystem.ColorSupport.Terminal.RESET}"

      ThemeSystem.ColorSupport.Terminal.colored(text, color) should be(expected)
    }

    "colored with background should correctly wrap text with all codes" in {
      val text = "World"
      val color = ThemeSystem.ColorSupport.Terminal.ITALIC
      val bgcolor = "\u001B[48;5;100m" // An arbitrary background code
      val expected = s"$color$bgcolor$text${ThemeSystem.ColorSupport.Terminal.RESET}"

      ThemeSystem.ColorSupport.Terminal.colored(text, color, bgcolor) should be(expected)
    }

    "contain correct ANSI escape codes for styles" in {
      // This test just checks the values of constants and is fine.
      val styleTable =
        Table(
          ("Style Constant", "Expected Code"),
          (ThemeSystem.ColorSupport.Terminal.RESET, "\u001B[0m"),
          (ThemeSystem.ColorSupport.Terminal.BOLD, "\u001B[1m"),
          (ThemeSystem.ColorSupport.Terminal.ITALIC, "\u001B[3m"),
          (ThemeSystem.ColorSupport.Terminal.UNDERLINE, "\u001B[4m"),
          (ThemeSystem.ColorSupport.Terminal.BLINK, "\u001B[5m")
        )

      forAll(styleTable) { (styleConstant, expectedCode) =>
        styleConstant should be(expectedCode)
      }
    }
  }
}