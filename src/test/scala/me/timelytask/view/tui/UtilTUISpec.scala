package me.timelytask.view.tui

import com.github.nscala_time.time.Imports.*
import me.timelytask.view.tui.UtilTUI.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class UtilTUISpec extends AnyWordSpec {

  "The UtilTUI" should {
    "return a string with the specified number of dashes" in {
      createLine(5) should be("-----")
      createLine(0) should be("")
      createLine(10) should be("----------")
    }

    "throw an exception when createLine length is negative" in {
      an[IllegalArgumentException] should be thrownBy {
        createLine(-1)
      }
      "align the text to the left" in {
        UtilTUI.columnSpacer("Hello", 10, "l") should be("Hello     ")
        UtilTUI.columnSpacer("Hello", 5, "l") should be("Hello")
        UtilTUI.columnSpacer("Hello", 0, "l") should be("Hello")
      }

      "align the text to the middle" in {
        UtilTUI.columnSpacer("Hello", 10, "m") should be("  Hello   ")
        UtilTUI.columnSpacer("Hello", 5, "m") should be("Hello")
        UtilTUI.columnSpacer("Hello", 0, "m") should be("Hello")
      }

      "align the text to the right" in {
        UtilTUI.columnSpacer("Hello", 10, "r") should be("     Hello")
        UtilTUI.columnSpacer("Hello", 5, "r") should be("Hello")
        UtilTUI.columnSpacer("Hello", 0, "r") should be("Hello")
      }
    }

    "return a string with the specified number of spaces" in {
      createSpace(5) should be("     ")
      createSpace(0) should be("")
      createSpace(10) should be("          ")
    }

    "throw an exception when createSpace length is negative" in {
      an[IllegalArgumentException] should be thrownBy {
        createSpace(-1)
      }
    }

    "return a list of days starting with a given day and going forward for a given TimePeriod" in {
      val date = new DateTime(2024, 10, 14, 0, 0)
      getDaySpan(date, 3) should be(List(new DateTime(2024, 10, 14, 0, 0), new DateTime(2024, 10,
        15, 0, 0), new DateTime(2024, 10, 16, 0, 0)))
    }

    "return a string with the specified number of newlines" in {
      alignTop(totalLines = 10, used = 5) should be("\n" * 2)
      alignTop(totalLines = 10, used = 0) should be("\n" * 7)
      alignTop(totalLines = 10, used = 10) should be("")
    }

    "cut the text if it is longer than the total space" in {
      cutText("Hello World", 5) should be("Hello")
      cutText("Hello World", 11) should be("Hello World")
      cutText("Hello World", 0) should be("")
    }
    "write a welcome Message" in {
      val userName = System.getProperty("user.name")
      welcomeMessage() should be(s"Hello $userName,\nWelcome to TimelyTask!\n")
    }
    "clear the terminal" in {
      clearTerminal() should be("\u001b[H\u001b[2J")
    }
  }
}