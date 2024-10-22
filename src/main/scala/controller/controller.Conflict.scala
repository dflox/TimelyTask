package controller

import view.*
import model.*
import com.github.nscala_time.time.Imports.*
import model.settings.FileType.{JSON, XML}

import java.awt.Color
import java.util.UUID
import scala.collection.immutable.HashSet
import scala.collection.mutable
import util.FileLoader
import io.circe.generic.auto.*
import model.settings.StartView.TABLE
import model.settings.Theme.DARK

case class Controller(view: View[List[Task]]) {

  var tasks: List[Task] = List()
  var tags: mutable.HashMap[UUID, Tag] = mutable.HashMap()
  var priorities: mutable.HashMap[UUID, Priority] = mutable.HashMap()
  var states: mutable.HashMap[UUID, State] = mutable.HashMap()
  val config: Config = Controller.getConfig

  def loadConfigTest(): Unit = {
    println("Running")
    println("Config: " + config)
    FileLoader.save[Config](JSON, "./", List(config))
    println("Saved")
    println("Loading")
    val loadedConfig = FileLoader.load[Config](JSON, "./").getOrElse(List(new Config(TABLE, defaultDataFileType = XML, DARK))).head
    println("Loaded: " + loadedConfig)
  }

  def run(): Unit = {

  }
}

object Controller {
  val defaultConfigFolderPath: String = "./"

  private def getConfig: Config = {
    try {
      FileLoader.load[Config](JSON, defaultConfigFolderPath).getOrElse(List(Config.defaultConfig)).head
    } catch
      case e: Exception => Config.defaultConfig
  }
}
