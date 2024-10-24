package view
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import com.github.nscala_time.time.Imports.*
import view.UtilTUI.*


class CalendarTUISpec extends AnyWordSpec {
  "The CalendarTUI" should {
    "align the text to the left" in {
      val calendarTUI = new CalendarTUI
      calendarTUI.columnSpacer("Hello", 10, "l") should be("Hello     ")
      calendarTUI.columnSpacer("Hello", 0, "l") should be("Hello")
      calendarTUI.columnSpacer("Hello", 5, "l") should be("Hello")
    }

  }
}