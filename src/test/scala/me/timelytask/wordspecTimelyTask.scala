package me.timelytask

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class wordspecTimelyTask extends AnyWordSpec {
  "The TimelyTask object" should {
    "run the main method without errors" in {
      noException should be thrownBy {
        TimelyTask.main(Array.empty)
      }
    }
  }
}
