package me.timelytask.view

import me.timelytask.controller.ViewModelPublisher
import me.timelytask.model.settings.ViewType
import me.timelytask.view.viewmodel.{CalendarViewModel, TUIModel, ViewModel}
import me.timelytask.view.tui.*
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers.shouldEqual
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.mockito.MockitoSugar.mock

class ViewManagerSpec extends AnyWordSpec with MockitoSugar {
  "The ViewManager" should {
    "render the active TUI view with and without a TUIModel" in {
      val viewModelPublisher = mock[ViewModelPublisher]
      val viewManager = new ViewManager(viewModelPublisher)
      val tuiModel = mock[TUIModel]
      val calendarTUI = mock[TUIView]
      val model = mock[CalendarViewModel]
      when(viewModelPublisher.getCurrentViewModel).thenReturn(model)
      when(calendarTUI.update(model,tuiModel)).thenReturn("new calendar view")
      when(calendarTUI.update(model,TUIModel.default)).thenReturn("default calendar view")
      val viewType = mock[ViewType]
      when(viewType.getTUIView).thenReturn(calendarTUI)
      viewManager.onActiveViewChange(viewType)

      viewManager.renderActiveTUIView() shouldEqual "default calendar view"
      viewManager.renderActiveTUIView(tuiModel) shouldEqual "new calendar view"
    }
  }

}
