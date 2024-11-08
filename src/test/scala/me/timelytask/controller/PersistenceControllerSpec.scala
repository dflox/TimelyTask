package me.timelytask.controller

import me.timelytask.model.Model
import me.timelytask.model.settings.{Exit, NextDay}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import me.timelytask.view.viewmodel.ViewModel
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers.shouldEqual

class PersistenceControllerSpec extends AnyWordSpec with MockitoSugar {
  "The PersistenceManager" should {
    "handle exit actions correctly" in {
      val viewModel = mock[ViewModel]
      val viewModelPublisher = mock[ViewModelPublisher]
      when(viewModelPublisher.getCurrentViewModel).thenReturn(viewModel)

      val activeViewPublisher = mock[ActiveViewPublisher]
      val modelPublisher = mock[ModelPublisher]

      val persistenceController = new PersistenceController(viewModelPublisher, modelPublisher, activeViewPublisher)

      persistenceController.onModelChange(mock[Model])
      persistenceController.handleAction(Exit) shouldEqual None
      persistenceController.handleAction(NextDay) shouldEqual Some(viewModel)
    }
  }

}
