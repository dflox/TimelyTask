package me.timelytask.model.settings

import me.timelytask.model.settings.ViewType
import me.timelytask.view.views.viewImpl.tui.CalendarViewStringFactory
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ViewTypeSpec extends AnyWordSpec {
  "The StartView" should {
    "be en- and decodable to String" in {
      ViewType.fromString(CALENDAR.toString) should be(CALENDAR)
      ViewType.fromString(TABLE.toString) should be(TABLE)
      ViewType.fromString(KANBAN.toString) should be(KANBAN)
      ViewType.fromString(SETTINGS.toString) should be(SETTINGS)
      ViewType.fromString(TASKEdit.toString) should be(TASKEdit)
    }
  }


}
