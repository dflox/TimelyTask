package me.timelytask.controller

import me.timelytask.model.Model
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.util.Publisher
import me.timelytask.controller.commands.{Exit, SaveAndExit, StartApp, Handler}

class PersistenceController(using modelPublisher: Publisher[Model]) 
  extends Controller {
  
  StartApp.setHandler((args: Unit) => {
    modelPublisher.update(Some(Model.default))
    true
  })

  SaveAndExit.setHandler((args: Unit) => {
    if(save()) {
      Exit.createCommand(()).doStep()
    }else {
      false
    }
  })

  private def save(): Boolean = {
    true
  }

  private def load(): Boolean = {
    true
  }
}
