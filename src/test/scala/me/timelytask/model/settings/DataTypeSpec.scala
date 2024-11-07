package me.timelytask.model.settings

import me.timelytask.model.settings.DataType
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class DataTypeSpec extends AnyWordSpec{
  "The DataType" should {
    "print the correct data type" in {
      DataType.TAG.toString should be("tag")
      DataType.TASK.toString should be("task")
      DataType.STATE.toString should be("state")
      DataType.PRIORITY.toString should be("priority")
      DataType.CONFIG.toString should be("config")
    }
    "create the correct data type from a string" in {
      DataType.fromString("tag") should be(DataType.TAG)
      DataType.fromString("task") should be(DataType.TASK)
      DataType.fromString("state") should be(DataType.STATE)
      DataType.fromString("priority") should be(DataType.PRIORITY)
      DataType.fromString("config") should be(DataType.CONFIG)
    }
  }
}
