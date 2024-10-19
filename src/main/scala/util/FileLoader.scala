package util

import model.{Data, XmlSerializable, YamlSerializable}
import model.settings.{DataType, FileType}

import scala.util.{Failure, Success, Try}
import scala.io.Source
import java.io.PrintWriter
import io.circe.syntax.*
import io.circe.Encoder
import io.circe.parser.decode
import io.circe.Decoder
import io.circe.generic.auto._

import scala.reflect.ClassTag

object FileLoader {

  private def loadFile(filePath: String): Try[String] = {
    Try {
      val source = Source.fromFile(filePath)
      try source.mkString finally source.close()
    }
  }

  private def saveFile(filePath: String, content: String): Try[Boolean] = {
    Try {
      new PrintWriter(filePath) {
        write(content)
        close()
      }
      true
    }
  }

  private def encodeListToXml[T](list: List[T])(implicit ser: XmlSerializable[T]): String = {
    val encodedItems = list.map { item =>
      s"<item>${ser.XmlEncoder(item)}</item>"
    }.mkString("\n")

    s"<list>\n$encodedItems\n</list>"
  }

  private def decodeListFromXml[T](xml: String)(implicit ser: XmlSerializable[T]): List[T] = {
    val xmlElem = scala.xml.XML.loadString(xml)
    val items = (xmlElem \ "item").map(node => ser.XmlDecoder(node.text)).toList
    items
  }

  private def encodeListToJson[T: Encoder](list: List[T]): String = {
    list.asJson.noSpaces
  }

  private def decodeListFromJson[T: Decoder](json: String): Either[io.circe.Error, List[T]] = {
    decode[List[T]](json)
  }

  private def encodeListToYaml[T](list: List[T])(implicit ser: YamlSerializable[T]): String = {
    list.map(item => ser.YamlEncoder(item)).mkString("\n")
  }

  private def decodeListFromYaml[T: ClassTag](yaml: String)(implicit ser: YamlSerializable[T]): List[T] = {
    yaml.split("\n").map(ser.YamlDecoder).toList
  }


  def save[T: Decoder : Encoder : YamlSerializable : XmlSerializable](fileType: FileType, folderPath: String, data: List[T])(implicit dat: Data[T]): Try[String] = {
    val filePath = s"$folderPath/${dat.dataType.toString}.${fileType.toString.toLowerCase}"
    fileType match {
      case FileType.JSON => saveFile(filePath, encodeListToJson(data)) match {
        case Success(_) => Success(filePath)
        case Failure(exception) => Failure(exception)
      }
      case FileType.XML => saveFile(filePath, encodeListToXml(data)) match {
        case Success(_) => Success(filePath)
        case Failure(exception) => Failure(exception)
      }
      case FileType.YAML => saveFile(filePath, encodeListToYaml(data)) match {
        case Success(_) => Success(filePath)
        case Failure(exception) => Failure(exception)
      }
    }
  }

  def load[T: Decoder : Encoder : YamlSerializable : XmlSerializable : ClassTag](fileType: FileType, folderPath: String)(implicit dat: Data[T]): Try[List[T]] = {
    val filePath = s"$folderPath/${dat.dataType.toString}.${fileType.toString.toLowerCase}"
    loadFile(filePath) match {
      case Success(content) => fileType match {
        case FileType.JSON => decodeListFromJson[T](content) match {
          case Right(data) => Success(data)
          case Left(error) => Failure(new Exception(error.toString))
        }
        case FileType.XML => Success(decodeListFromXml[T](content))
        case FileType.YAML => Success(decodeListFromYaml[T](content))
      }
      case Failure(exception) => Failure(exception)
    }
  }
}

//  def load(datatype: DataType, filetype: FileType, folderPath: String): Try[List[_]] = {
//    val filePath = s"$folderPath/${datatype.toString.toLowerCase}.${filetype.toString.toLowerCase}"
//    loadFile(filePath) match {
//      case Success(content) => filetype match {
//        case FileType.JSON => Success(content.split("\n").map(datatype.getJsonDecoder).toList)
//        case FileType.XML => Success(content.split("\n").map(datatype.getXmlDecoder).toList)
//        case FileType.YAML => Success(content.split("\n").map(datatype.getYamlDecoder).toList)
//      }
//      case Failure(exception) => Failure(exception)
//    }
//
//  }
//
//  def save(datatype: DataType, filetype: FileType, folderPath: String, data: List[?]): Try[String] = {
//    var dataString = ""
//    filetype match {
//      case FileType.JSON => dataString = data.map(datatype.getJsonEncoder).mkString("\n") // Does this create a JSON object
//      case FileType.XML => dataString = data.map(datatype.getXmlEncoder).mkString("\n")
//      case FileType.YAML => dataString = data.map(datatype.getYamlEncoder).mkString("\n")
//    }
//    val filePath = s"$folderPath/${datatype.toString.toLowerCase}.${filetype.toString.toLowerCase}"
//    saveFile(filePath, dataString) match {
//      case Success(_) => Success(filePath)
//      case Failure(exception) => Failure(exception)
//    }
//  }

