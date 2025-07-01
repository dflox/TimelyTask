package me.timelytask.core

import me.timelytask.model.settings.{CALENDAR, UIType, ViewType}
import me.timelytask.model.settings.UIType.{GUI, TUI}

case class StartUpConfig(uiInstances: List[UiInstanceConfig])

case object StartUpConfig {
  val default: StartUpConfig = StartUpConfig(
    uiInstances = List(UiInstanceConfig(List(GUI, TUI), CALENDAR))
  )
}

case class UiInstanceConfig(uis: List[UIType], startView: ViewType)
