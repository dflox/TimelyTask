package me.timelytask.core

import me.timelytask.model.settings.{CALENDAR, UIType, ViewType}
import me.timelytask.model.settings.UIType.TUI

case class StartUpConfig(uiInstances: List[UiInstanceConfig]) {
  def validate(): Unit = {
    if(uiInstances.flatMap (i => i.uis.filter (ui => ui.eq (TUI) ) ).length > 1) 
      throw new Exception ("Only one TUI instance is allowed!")
  }
}

case object StartUpConfig {
  val default: StartUpConfig = StartUpConfig(
    uiInstances = List(UiInstanceConfig(List(UIType.TUI), CALENDAR))
  )
}

case class UiInstanceConfig(uis: List[UIType], startView: ViewType)
