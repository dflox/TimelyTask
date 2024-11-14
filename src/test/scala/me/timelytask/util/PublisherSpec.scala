package me.timelytask.util

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.*

class PublisherSpec extends AnyWordSpec with MockitoSugar {
  "The Publisher" should {
    "add a listener and notify it on update" in {
      val publisher = new Publisher[Int](0)
      val listener = mock[Int => Unit]

      publisher.addListener(listener)
      publisher.update(1)

      verify(listener).apply(1)
    }

    "update the value and notify all listeners" in {
      val publisher = new Publisher[String]("initial")
      val listener1 = mock[String => Unit]
      val listener2 = mock[String => Unit]

      publisher.addListener(listener1)
      publisher.addListener(listener2)
      publisher.update("updated")

      verify(listener1).apply("updated")
      verify(listener2).apply("updated")
    }

    "retrieve the current value" in {
      val publisher = new Publisher[Double](3.14)
      publisher.getValue shouldEqual 3.14

      publisher.update(2.71)
      publisher.getValue shouldEqual 2.71
    }
  }
}