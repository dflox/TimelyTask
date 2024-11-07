package me.timelytask.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import me.timelytask.model.Model
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify
import org.scalatest.matchers.should.Matchers.shouldEqual

class ModelPublisherSpec extends AnyWordSpec with MockitoSugar{
  "The ModelPublisher" should {
    "update the view model" in {
      val model = mock[Model]
      val mocObserver = mock[ModelObserver]
      val modelPublisher = new ModelPublisher(Model.default)
      modelPublisher.getCurrentModel shouldEqual Model.default
      
      modelPublisher.subscribe(mocObserver)
      modelPublisher.updateModel(model)
      val captor = ArgumentCaptor.forClass(classOf[Model])
      verify(mocObserver).onModelChange(captor.capture())
      captor.getValue shouldEqual model
      
      modelPublisher.getCurrentModel shouldEqual model
    }

}
}
