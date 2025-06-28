package me.timelytask.util.color

import me.timelytask.model.settings.ThemeSystem.{ColorSupport, Theme}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import scalafx.scene.paint.Color

class ThemeApplierSpec extends AnyFunSuite with Matchers with BeforeAndAfterEach {

  // The default theme, created with no arguments.
  private val defaultTheme = Theme()

  // A custom theme, created using the actual field names from your Theme class.
  private val customTheme = Theme(
    background1 = "#111111", // Using a real field for testing
    accent0 = "#222222",     // Using another real field
    text1 = "#333333",       // And another
    accent2 = "#444444"      // For the final test case
  )

  /**
   * This is crucial for testing a singleton with mutable state.
   * Before each test runs, we reset the ThemeApplier to its default state
   * to ensure that tests do not influence each other.
   */
  override def beforeEach(): Unit = {
    ThemeApplier.setTheme(defaultTheme)
  }

  test("ThemeApplier should initialize with and be resettable to a default theme") {
    ThemeApplier.getTheme should be(defaultTheme)
  }

  test("setTheme should update the current theme, and getTheme should retrieve it") {
    ThemeApplier.getTheme should not be customTheme

    // Action
    ThemeApplier.setTheme(customTheme)

    // Assert
    val retrievedTheme = ThemeApplier.getTheme
    retrievedTheme should be(customTheme)
    retrievedTheme.accent0 should be("#222222")
  }



  test("getThemeValue should extract the correct hex string from the current theme") {
    // This test verifies the core logic of all getColor methods without calling
    // the problematic external converters (e.g., for the terminal or JavaFX).

    // Setup
    ThemeApplier.setTheme(customTheme)
    val colorSelectorFunction = (t: Theme) => t.accent0

    // Action
    val resultHexValue = ThemeApplier.getThemeValue(colorSelectorFunction)

    // Assert
    // We confirm that the correct hex string was selected from the theme.
    resultHexValue should be(customTheme.accent0)
    resultHexValue should be("#222222")
  }

}