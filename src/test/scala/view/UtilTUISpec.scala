package view
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import com.github.nscala_time.time.Imports.*
import view.UtilTUI.*

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
      
      "return a time period in a given Format as String starting with a given day and going forward for a given TimePeriod" in {
        val date = new DateTime(2024, 10, 14, 0, 0)
        getDatePeriod(date, 3, "dd.", "dd. MMM yy", " - ") should be("14. - 16. Okt. 24")
      }
      
      "return a string with the specified number of newlines" in {
        alignTop(totalLines = 10, used = 5) should be("\n" * 5)
        alignTop(totalLines = 10, used = 10) should be("")
        alignTop(totalLines = 10, used = 0) should be("\n" * 10)
      }
    }
}