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
  
  private def buildFileName(fileName: Option[String]): String = fileName
  .getOrElse(s"${timeStamp}_$applicationName")

  override def saveModel(userToken: String,
                         folderPath: Option[String] = None,
                         fileName: Option[String] = None,
                         serializationType: String)
  : Boolean = {
    serviceModule.fileExportService.exportToFile(userToken,
      folderPath.getOrElse("").concat(buildFileName(fileName)),
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
