package me.timelytask.core

import me.timelytask.model.settings.UIType

case class StartUpConfig(uiInstances: List[UIInstanceConfig],
                         serializationType: String)
case object StartUpConfig {
  val default: StartUpConfig = StartUpConfig(
    uiInstances = List(UIInstanceConfig(List(UIType.TUI))),
    serializationType = "json"
  )
}

case class UIInstanceConfig(uis: List[UIType])
