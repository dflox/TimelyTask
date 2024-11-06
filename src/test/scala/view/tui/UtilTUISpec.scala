package view.tui
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import com.github.nscala_time.time.Imports._
import view.tui.UtilTUI._

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

      "return the first day of the week" in {
        val date = new DateTime(2024, 10, 18, 0, 0)
        getFirstDayOfWeek(date) should be(new DateTime(2024, 10, 14, 0, 0))
      }
      
      "return a list of days starting with a given day and going forward for a given TimePeriod" in {
        val date = new DateTime(2024, 10, 14, 0, 0)
        getDaySpan(date, 3) should be(List(new DateTime(2024, 10, 14, 0, 0), new DateTime(2024, 10, 15, 0, 0), new DateTime(2024, 10, 16, 0, 0)))
      }
      
      "return a string with the specified number of newlines" in {
        alignTop(totalLines = 10, used = 5) should be("\n" * 5)
        alignTop(totalLines = 10, used = 0) should be("\n" * 10)
        alignTop(totalLines = 10, used = 10) should be("")
        alignTop(totalLines = 10, used = 11) should be("")
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