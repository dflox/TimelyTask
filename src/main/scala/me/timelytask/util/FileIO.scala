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
  
  def tryFindFile(fileType: String): Option[String] = {
    val fileName = s".$fileType"
    val currentDir = Paths.get("").toAbsolutePath
    val files = Files.list(currentDir).toArray
    files.collectFirst {
      case path if path.toString.endsWith(fileName) && path.toString.contains("_TimelyTask_") => 
        path.toString
    }
  }
}
