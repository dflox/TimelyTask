package me.timelytask.controller

import me.timelytask.controller.commands.{Exit, Handler, SaveAndExit, StartApp}
import me.timelytask.model.Model
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.viewmodel.ViewModel

class PersistenceControllerImpl(modelPublisher: Publisher[Model])
  extends Controller(modelPublisher)
  with PersistenceController {

  override def init(): Unit = {
    StartApp.setHandler((args: Unit) => {
      modelPublisher.update(Some(Model.default))
      true
    })

    SaveAndExit.setHandler((args: Unit) => {
      if (save()) {
        Exit.createCommand(()).execute
      } else {
        false
      }
    })
  }

  private def save(): Boolean = {
    true
  }

  private def load(): Boolean = {
    true
  }
}
