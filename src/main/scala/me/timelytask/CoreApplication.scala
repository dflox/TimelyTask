package me.timelytask

import me.timelytask.controller.commands.UndoManager
import me.timelytask.controller.{ModelController, PersistenceController}
import me.timelytask.model.Model
import me.timelytask.util.Publisher

class CoreApplication {
  given modelPublisher: Publisher[Model] = Publisher[Model](Some(Model.default))

  given taskController: ModelController = new ModelController()

  given persistenceController: PersistenceController = new PersistenceController()

  def run(): Unit = {
    summon[Publisher[Model]]
    summon[ModelController]
    summon[PersistenceController]
    val uiInstance: UiInstance = new UiInstance()
    uiInstance.run()
  }
}
