package me.timelytask.model.settings

import me.timelytask.model.settings.ViewType
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ViewTypeSpec extends AnyWordSpec{
  "The StartView" should {
    "print the correct start view" in {
      ViewType.CALENDAR.toString should be("calendar")
      ViewType.TABLE.toString should be("table")
      ViewType.KANBAN.toString should be("kanban")
    }
    "create the correct start view from a string" in {
      ViewType.fromString("calendar") should be(ViewType.CALENDAR)
      ViewType.fromString("table") should be(ViewType.TABLE)
      ViewType.fromString("kanban") should be(ViewType.KANBAN)
    }
  }


}
