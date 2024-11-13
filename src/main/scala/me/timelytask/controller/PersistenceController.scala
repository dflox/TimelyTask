package me.timelytask.controller

import me.timelytask.TimelyTask
import me.timelytask.model.Model
import me.timelytask.model.settings.{Action, Exit, SaveAndExit, StartApp, ViewType}
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.model.modelPublisher

object PersistenceController extends Controller {

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
