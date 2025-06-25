package me.timelytask.controller.controllersImpl

import me.timelytask.controller.commands.CommandHandler
import me.timelytask.controller.PersistenceController
import me.timelytask.model.Model
import me.timelytask.util.{FileIO, Publisher}
import me.timelytask.util.serialization.SerializationStrategy
import me.timelytask.util.serialization.encoder.given
import me.timelytask.util.serialization.decoder.given
import com.github.nscala_time.time.Imports.DateTime

class PersistenceControllerImpl(modelPublisher: Publisher[Model],
                                commandHandler: CommandHandler)
  extends Controller(modelPublisher, commandHandler)
  with PersistenceController {

  override private[controller] def init(): Unit = modelPublisher.addListener(saveToDB)

  private def saveToDB(model: Option[Model]): Unit = model.map(m => ())

  private[controller] def loadModelFromDB(): Unit = modelPublisher.update(Some(Model.emptyModel))

  private var serializer: Option[SerializationStrategy] = None
  
  private val applicationName = "TimelyTask"
  
  private def timeStamp: String = DateTime.now().toString("yyyy_mm_dd")
  
  private def buildFileName(fileName: Option[String]): String = fileName
  .getOrElse(s"${timeStamp}_$applicationName.${serializer.get.fileExtension}")

  override def saveModel(folderPath: Option[String], fileName: Option[String]): Boolean = {
    if (serializer.isEmpty) throw new IllegalStateException("Serialization strategy not set") 
    
    val serializedModel = serializer.get.serialize(model().getOrElse(Model.emptyModel))
    
    FileIO.writeToFile(folderPath.getOrElse("").concat(buildFileName(fileName)),
      serializedModel)
  }

  override def loadModel(folderPathWithFileName: String): Boolean = {
    if (serializer.isEmpty) throw new IllegalStateException("Serialization strategy not set")

    val serializedModel = FileIO.readFromFile(folderPathWithFileName)

    if (serializedModel.isEmpty) throw new IllegalArgumentException(
      s"File not found or empty: $folderPathWithFileName"
    )
    
    serializer.get.deserialize[Model](serializedModel.get) match {
      case Some(model) =>
        modelPublisher.update(Some(model))
        true
      case None =>
        throw new IllegalArgumentException(
          s"Failed to deserialize model from file: $folderPathWithFileName"
        )
    }
  } 

  override def setSerializationType(serializationType: String): Boolean = {
    serializer = SerializationStrategy.tryApply(serializationType)
    serializer match {
      case Some(_) => true
      case None => false
    }
  }

  override private[controller] def provideModelFromDB(userName: String): Unit = ???
}
