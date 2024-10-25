package controller

import view.*
import model.*
import com.github.nscala_time.time.Imports.*
import model.settings.FileType.{JSON, XML}

import java.awt.Color
import java.util.UUID
import scala.collection.immutable.HashSet
import scala.collection.mutable
import io.circe.generic.auto.*
import model.settings.StartView.TABLE
import model.settings.Theme.DARK

class Controller(view: View[List[Task]]) {

  var tasks: List[Task] = List()
  var tags: mutable.HashMap[UUID, Tag] = mutable.HashMap()
  var priorities: mutable.HashMap[UUID, Priority] = mutable.HashMap()
  var states: mutable.HashMap[UUID, State] = mutable.HashMap()
  //val config: Config = Controller.getConfig

  def run(): Unit = {
    
  }
}

object Controller {
  val defaultConfigFolderPath: String = "./"
}