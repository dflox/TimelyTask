package me.timelytask.view

import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.view.tui.*
import me.timelytask.view.viewmodel.{CalendarViewModel, TUIModel, ViewModel}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers.shouldEqual
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.mockito.MockitoSugar.mock

class ViewManagerSpec extends AnyWordSpec
                      with MockitoSugar {
  "The ViewManager" should {
    "render the active TUI view with and without a TUIModel" in {
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val viewManager = new ViewManager(viewModelPublisher)
      val tuiModel = mock[TUIModel]
      val calendarTUI = mock[TUIView]
      val model = mock[CalendarViewModel]
      when(viewModelPublisher.getValue).thenReturn(model)
      when(calendarTUI.update(model, tuiModel)).thenReturn("new calendar view")
      when(calendarTUI.update(model, TUIModel.default)).thenReturn("default calendar view")
      val viewType = mock[ViewType]
      when(viewType.getTUIView).thenReturn(calendarTUI)
      viewManager.onChange(viewType)

      viewManager.renderActiveTUIView() shouldEqual "default calendar view"
      viewManager.renderActiveTUIView(tuiModel) shouldEqual "new calendar view"
    }
  }

}
