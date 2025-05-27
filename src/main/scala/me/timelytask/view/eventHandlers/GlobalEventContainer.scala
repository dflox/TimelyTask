package me.timelytask.view.eventHandlers

trait GlobalEventContainer {

  def undo(): Unit

  def redo(): Unit

  def switchToView(viewType: _root_.me.timelytask.model.settings.ViewType): Unit

  def shutdown(): Unit
}
