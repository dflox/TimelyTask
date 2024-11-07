package me.timelytask.controller

import me.timelytask.model.settings.ViewType
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify
import org.scalatest.matchers.should.Matchers.shouldEqual
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

class ActiveViewPublisherSpec extends AnyWordSpec with MockitoSugar{
  "The ActiveViewPublisher" should {
    "update the view model" in {
      val viewType = mock[ViewType]
      val mocObserver = mock[ActiveViewObserver]
      val activeViewPublisher = new ActiveViewPublisher
      activeViewPublisher.getActiveView shouldEqual ViewType.CALENDAR

      activeViewPublisher.subscribe(mocObserver)
      activeViewPublisher.updateActiveView(viewType)
      val captor = ArgumentCaptor.forClass(classOf[ViewType])
      verify(mocObserver).onActiveViewChange(captor.capture())
      captor.getValue shouldEqual viewType
      
      activeViewPublisher.getActiveView shouldEqual viewType
    }
    }
}
