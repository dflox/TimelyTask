package model.settings

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class StartViewSpec extends AnyWordSpec{
  "The StartView" should {
    "print the correct start view" in {
      StartView.CALENDAR.toString should be("calendar")
      StartView.TABLE.toString should be("table")
      StartView.KANBAN.toString should be("kanban")
    }
    "create the correct start view from a string" in {
      StartView.fromString("calendar") should be(StartView.CALENDAR)
      StartView.fromString("table") should be(StartView.TABLE)
      StartView.fromString("kanban") should be(StartView.KANBAN)
    }
  }


}
