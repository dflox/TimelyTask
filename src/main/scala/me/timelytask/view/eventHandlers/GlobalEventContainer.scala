package me.timelytask.view.eventHandlers

import me.timelytask.model.settings.ViewType

trait GlobalEventContainer {

  def undo(): Unit

  def redo(): Unit

  def switchToView(): Unit
  
  def newWindow(): Unit
  
  def newInstance(): Unit
  
  def addRandomTask(): Unit
  
  def newTask(): Unit

  def shutdownApplication(): Unit
}
