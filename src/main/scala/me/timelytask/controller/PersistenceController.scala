package me.timelytask.controller

import me.timelytask.TimelyTask
import me.timelytask.controller.mediator.Mediator
import me.timelytask.model.Model
import me.timelytask.model.settings.{Action, Exit, SaveAndExit, StartApp, ViewType}
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.util.Publisher

class PersistenceController(using modelPublisher: Publisher[Model], 
                            viewModelPublisher: Publisher[ViewModel], mediator: Mediator) 
  extends Controller {
  
  println("PersistenceController created")
  
  StartApp.setHandler(() => {
    modelPublisher.update(Model.default)
    true
  })

  SaveAndExit.setHandler(() => {
    if(save()) {
      Exit.call
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
