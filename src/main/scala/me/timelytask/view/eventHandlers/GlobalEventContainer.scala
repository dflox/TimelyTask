package me.timelytask.view.eventHandlers

import me.timelytask.model.settings.ViewType

trait GlobalEventContainer {

  def undo(): Unit

  def redo(): Unit

  def switchToView(viewType: ViewType): Unit

  def shutdown(): Unit
}
