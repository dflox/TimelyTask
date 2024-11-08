package me.timelytask.util

import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify
import org.scalatest.matchers.should.Matchers.shouldEqual
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.mockito.MockitoSugar.mock

class PublisherSpec extends AnyWordSpec
                    with MockitoSugar {
  "The ViewModelPublisher" should {
    "update the view model" in {
      val viewModel1 = mock[ViewModel]
      val viewModel2 = mock[ViewModel]
      val mocObserver = mock[Observer[ViewModel]]
      val viewModelPublisher = new Publisher[ViewModel](viewModel1)
      viewModelPublisher.getValue shouldEqual viewModel1

      viewModelPublisher.subscribe(mocObserver)
      viewModelPublisher.update(viewModel2)
      val captor = ArgumentCaptor.forClass(classOf[ViewModel])
      verify(mocObserver).onChange(captor.capture())
      captor.getValue shouldEqual viewModel2

      viewModelPublisher.getValue shouldEqual viewModel2
    }
  }

}
