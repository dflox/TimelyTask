package me.timelytask

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class TimelyTaskSpec extends AnyWordSpec {

  "The TimelyTask object" should {

    "set running to false when exit is called" in {
      TimelyTask.running = true
      TimelyTask.exit()
      TimelyTask.running shouldEqual false
    }
  }
}