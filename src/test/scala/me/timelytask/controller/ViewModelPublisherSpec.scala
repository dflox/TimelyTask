package me.timelytask.controller

import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify
import org.scalatest.matchers.should.Matchers.shouldEqual
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.mockito.MockitoSugar.mock

class ViewModelPublisherSpec extends AnyWordSpec with MockitoSugar{
  "The ViewModelPublisher" should {
    "update the view model" in {
      val viewModel1 = mock[ViewModel]
      val viewModel2 = mock[ViewModel]
      val mocObserver = mock[ViewModelObserver]
      val viewModelPublisher = new ViewModelPublisher(viewModel1)
      viewModelPublisher.getCurrentViewModel shouldEqual viewModel1

      viewModelPublisher.subscribe(mocObserver)
      viewModelPublisher.updateViewModel(viewModel2)
      val captor = ArgumentCaptor.forClass(classOf[ViewModel])
      verify(mocObserver).onViewModelChange(captor.capture())
      captor.getValue shouldEqual viewModel2
      
      viewModelPublisher.getCurrentViewModel shouldEqual viewModel2
    }
    }

}
