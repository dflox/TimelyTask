//// src/test/scala/me/timelytask/model/settings/ThemeSystemSpec.scala
//
//package me.timelytask.model.settings
//
//import org.scalatest.wordspec.AnyWordSpec
//import org.scalatest.matchers.should.Matchers
//import org.scalatest.prop.TableDrivenPropertyChecks
//import scalafx.scene.paint.Color
//
///**
// * A complete and "real" test suite for the ThemeSystem object.
// * This test assumes the test environment is correctly configured to handle
// * JavaFX/ScalaFX classes without causing an UnsupportedClassVersionError.
// */
//class ThemeSystemSpec extends AnyWordSpec with Matchers with TableDrivenPropertyChecks {
//
//  "ThemeSystem.Theme" should {
//
//    "be created with correct default hex values" in {
//      // Test a representative sample of default values.
//      val defaultTheme = ThemeSystem.Theme()
//
//      defaultTheme.background0 should be("#17171c")
//      defaultTheme.text1 should be("#4BA6FB")
//      defaultTheme.accent0 should be("#BA274A")
//      defaultTheme.error should be("#f7768e")
//    }
//  }
//
//  "ThemeSystem.ColorSupport.ScalaFX" should {
//    "hexToColor should correctly convert a hex string to a ScalaFX Color" in {
//      val redHex = "#FF0000"
//      val blueHex = "#0000FF"
//      val greenHex = "#00FF00"
//
//      val redColor = ThemeSystem.ColorSupport.ScalaFX.hexToColor(redHex)
//      val blueColor = ThemeSystem.ColorSupport.ScalaFX.hexToColor(blueHex)
//
//      redColor shouldBe a[Color]
//      // ScalaFX Color components are Doubles from 0.0 to 1.0
//      redColor.getRed should be(1.0)
//      redColor.getGreen should be(0.0)
//      redColor.getBlue should be(0.0)
//
//      blueColor.getBlue should be(1.0)
//      blueColor.getRed should be(0.0)
//    }
//  }
//
//  "ThemeSystem.ColorSupport.Terminal" should {
//
//    "hexToTerminal256 should correctly convert hex codes to 256-color ANSI sequences" in {
//      // Use TableDrivenPropertyChecks for clean, data-driven testing.
//      val colorConversions =
//        Table(
//          ("Input Hex", "Expected Ansi Code"),
//          ("#FF0000", "\u001B[38;5;196m"), // Pure Red -> 16 + (36*5) + (6*0) + 0 = 196
//          ("#00FF00", "\u001B[38;5;46m"),  // Pure Green -> 16 + (36*0) + (6*5) + 0 = 46
//          ("#0000FF", "\u001B[38;5;21m"),  // Pure Blue -> 16 + (36*0) + (6*0) + 5 = 21
//          ("#000000", "\u001B[38;5;16m"),  // Black -> Grayscale special case
//          ("#FFFFFF", "\u001B[38;5;231m"), // White -> Grayscale special case
//          ("#888888", "\u001B[38;5;244m")  // A mid-gray -> 232 + round(136/255 * 23) = 232 + 12 = 244
//        )
//
//      forAll(colorConversions) { (hex, expectedAnsi) =>
//        ThemeSystem.ColorSupport.Terminal.hexToTerminal256(hex) should be(expectedAnsi)
//      }
//    }
//
//    "bgHexToTerminal256 should correctly convert a hex code to a background color" in {
//      // This test verifies that the method correctly performs the string replacement.
//      val redHex = "#FF0000"
//      val expectedBgAnsi = "\u001B[48;5;196m" // Note the [48 instead of [38
//
//      ThemeSystem.ColorSupport.Terminal.bgHexToTerminal256(redHex) should be(expectedBgAnsi)
//    }
//
//    "colored should correctly wrap text with color and reset codes" in {
//      val text = "Hello"
//      val color = ThemeSystem.ColorSupport.Terminal.BOLD
//      val expected = s"${ThemeSystem.ColorSupport.Terminal.BOLD}$text${ThemeSystem.ColorSupport.Terminal.RESET}"
//
//      ThemeSystem.ColorSupport.Terminal.colored(text, color) should be(expected)
//    }
//
//    "colored with background should correctly wrap text with all codes" in {
//      val text = "World"
//      val color = ThemeSystem.ColorSupport.Terminal.ITALIC
//      val bgcolor = "\u001B[48;5;100m" // An arbitrary background code
//      val expected = s"$color$bgcolor$text${ThemeSystem.ColorSupport.Terminal.RESET}"
//
//      ThemeSystem.ColorSupport.Terminal.colored(text, color, bgcolor) should be(expected)
//    }
//
//    "contain correct ANSI escape codes for styles" in {
//      val styleTable =
//        Table(
//          ("Style Constant", "Expected Code"),
//          (ThemeSystem.ColorSupport.Terminal.RESET, "\u001B[0m"),
//          (ThemeSystem.ColorSupport.Terminal.BOLD, "\u001B[1m"),
//          (ThemeSystem.ColorSupport.Terminal.ITALIC, "\u001B[3m"),
//          (ThemeSystem.ColorSupport.Terminal.UNDERLINE, "\u001B[4m"),
//          (ThemeSystem.ColorSupport.Terminal.BLINK, "\u001B[5m")
//        )
//
//      forAll(styleTable) { (styleConstant, expectedCode) =>
//        styleConstant should be(expectedCode)
//      }
//    }
//  }
//}