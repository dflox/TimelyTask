package me.timelytask.controller

import me.timelytask.model.Model
import me.timelytask.model.settings.{Exit, NextDay, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.ViewModel
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers.shouldEqual
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

class PersistenceControllerSpec extends AnyWordSpec with MockitoSugar {
  "The PersistenceManager" should {
    "handle exit actions correctly" in {
      val viewModel = mock[ViewModel]
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      when(viewModelPublisher.getValue).thenReturn(viewModel)

      val activeViewPublisher = mock[Publisher[ViewType]]

      val persistenceController = new PersistenceController(viewModelPublisher, modelPublisher, activeViewPublisher)

      persistenceController.onChange(mock[Model])
      persistenceController.handleAction(Exit) shouldEqual None
      persistenceController.handleAction(NextDay) shouldEqual Some(viewModel)
    }
  }

}
