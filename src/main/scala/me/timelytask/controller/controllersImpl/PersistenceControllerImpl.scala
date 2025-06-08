package me.timelytask.controller.controllersImpl

import me.timelytask.controller.commands.CommandHandler
import me.timelytask.controller.PersistenceController
import me.timelytask.model.Model
import me.timelytask.util.Publisher

class PersistenceControllerImpl(modelPublisher: Publisher[Model],
                                commandHandler: CommandHandler)
  extends Controller(modelPublisher, commandHandler)
  with PersistenceController {

  override private[controller] def init(): Unit = modelPublisher.addListener(saveToDB)

  private def saveToDB(model: Option[Model]): Unit = model.map((m) => ())

  private[controller] def loadModelFromDB(): Unit = modelPublisher.update(Some(Model.emptyModel))

  override def SaveModelTo(serializationType: String): Unit = ???

  override def LoadModel(serializationType: String): Unit = ???
}
