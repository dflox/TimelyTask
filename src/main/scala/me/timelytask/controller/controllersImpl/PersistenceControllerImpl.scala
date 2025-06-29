package me.timelytask.controller.controllersImpl

import me.timelytask.controller.commands.CommandHandler
import me.timelytask.controller.PersistenceController
import me.timelytask.model.Model
import me.timelytask.util.{FileIO, Publisher}
import me.timelytask.util.serialization.SerializationStrategy
import me.timelytask.util.serialization.encoder.given
import me.timelytask.util.serialization.decoder.given
import com.github.nscala_time.time.Imports.DateTime
import me.timelytask.model.user.User
import me.timelytask.serviceLayer.ServiceModule

class PersistenceControllerImpl(modelPublisher: Publisher[Model],
                                serviceModule: ServiceModule,
                                commandHandler: CommandHandler)
  extends Controller(modelPublisher, commandHandler)
  with PersistenceController {

  private val applicationName = "TimelyTask"

  private def timeStamp: String = DateTime.now().toString("yyyy_mm_dd")
  
  private def buildFileName(fileName: Option[String], userName: String): String = fileName
  .getOrElse(s"${timeStamp}_${applicationName}_$userName")

  override def saveModel(userToken: String,
                         folderPath: Option[String] = None,
                         fileName: Option[String] = None,
                         serializationType: String)
  : Boolean = {
    val user = serviceModule.userService.getUser(userToken)
    val folder = folderPath match {
      case Some(path) if path.nonEmpty => path + "/"
      case _ => ""
    }
    serviceModule.fileExportService.exportToFile(userToken,
      folder.concat(buildFileName(fileName, user.name)),
    serializationType)
    true
  }

  override def loadModel(userToken: String, folderPathWithFileName: String, 
                         serializationType: String): Boolean = {
    serviceModule.fileExportService.importFromFile(userToken, folderPathWithFileName, serializationType)
    true
  }

  override private[controller] def provideModelFromDB(userName: String): Unit = { 
    if(serviceModule.userService.userExists(userName)) serviceModule.modelService.loadModel(userName)
    else {
      val model = Model.emptyModel.copy(user = User(userName))
      serviceModule.modelService.saveModel(userName, model)
    }
  }
}
