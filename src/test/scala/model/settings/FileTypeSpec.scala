package model.settings

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class FileTypeSpec extends AnyWordSpec{
  "The FileType" should {
    "print the correct file type" in {
      FileType.XML.toString should be("xml")
      FileType.JSON.toString should be("json")
      FileType.YAML.toString should be("yaml")
    }
    "create the correct file type from a string" in {
      FileType.fromString("xml") should be(FileType.XML)
      FileType.fromString("json") should be(FileType.JSON)
      FileType.fromString("yaml") should be(FileType.YAML)
    }
  }

}