import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import util.FileLoader
import model.settings.FileType
import model.fileio.{XmlSerializable, YamlSerializable, Data}
import io.circe.generic.auto._
import scala.util.{Success, Failure}

// Sample case class for testing
case class TestData(name: String, value: Int)

// Implementing the necessary type classes for TestData
implicit val testDataXmlSerializable: XmlSerializable[TestData] = new XmlSerializable[TestData] {
  def XmlEncoder(data: TestData): String = s"<name>${data.name}</name><value>${data.value}</value>"
  def XmlDecoder(xml: String): TestData = {
    val name = (scala.xml.XML.loadString(xml) \ "name").text
    val value = (scala.xml.XML.loadString(xml) \ "value").text.toInt
    TestData(name, value)
  }
}

implicit val testDataYamlSerializable: YamlSerializable[TestData] = new YamlSerializable[TestData] {
  def YamlEncoder(data: TestData): String = s"name: ${data.name}\nvalue: ${data.value}"
  def YamlDecoder(yaml: String): TestData = {
    val lines = yaml.split("\n")
    val name = lines(0).split(": ")(1)
    val value = lines(1).split(": ")(1).toInt
    TestData(name, value)
  }
}

implicit val testDataData: Data[TestData] = new Data[TestData] {
  def dataType: String = "testdata"
}

class FileLoaderSpec extends AnyWordSpec {

  "The FileLoader" should {
    val testData = List(TestData("test1", 1), TestData("test2", 2))
    val folderPath = "test_folder"

    "save and load JSON files correctly" in {
      FileLoader.save(FileType.JSON, folderPath, testData) match {
        case Success(filePath) =>
          FileLoader.load[TestData](FileType.JSON, folderPath) match {
            case Success(loadedData) => loadedData shouldEqual testData
            case Failure(exception) => fail(s"Loading JSON failed: $exception")
          }
        case Failure(exception) => fail(s"Saving JSON failed: $exception")
      }
    }

    "save and load XML files correctly" in {
      FileLoader.save(FileType.XML, folderPath, testData) match {
        case Success(filePath) =>
          FileLoader.load[TestData](FileType.XML, folderPath) match {
            case Success(loadedData) => loadedData shouldEqual testData
            case Failure(exception) => fail(s"Loading XML failed: $exception")
          }
        case Failure(exception) => fail(s"Saving XML failed: $exception")
      }
    }

    "save and load YAML files correctly" in {
      FileLoader.save(FileType.YAML, folderPath, testData) match {
        case Success(filePath) =>
          FileLoader.load[TestData](FileType.YAML, folderPath) match {
            case Success(loadedData) => loadedData shouldEqual testData
            case Failure(exception) => fail(s"Loading YAML failed: $exception")
          }
        case Failure(exception) => fail(s"Saving YAML failed: $exception")
      }
    }
  }
}