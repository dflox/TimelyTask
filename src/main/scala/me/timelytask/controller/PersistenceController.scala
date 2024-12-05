package me.timelytask.controller

import me.timelytask.TimelyTask
import me.timelytask.model.Model
import me.timelytask.model.settings.{Exit, SaveAndExit, StartApp, ViewType}
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.util.Publisher
import me.timelytask.view.events.Event

class PersistenceController(using modelPublisher: Publisher[Model]) 
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
