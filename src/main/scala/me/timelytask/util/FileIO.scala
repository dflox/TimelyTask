package me.timelytask.util

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import scala.util.Try

object FileIO {
  def writeToFile(filePath: String, content: String): Boolean = {
    Try {
      Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8))  
    } match {
      case scala.util.Success(_) => true
      case scala.util.Failure(_) => false
    }
  }
  
  def readFromFile(filePath: String): Option[String] = {
    Try {
      new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8)
    }.toOption
  }
}
