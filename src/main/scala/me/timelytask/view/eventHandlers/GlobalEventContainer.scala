package me.timelytask.view.eventHandlers

trait GlobalEventContainer {

  def undo(): Unit

  def redo(): Unit

  def switchToView(): Unit
  
  def newWindow(): Unit
  
  def newInstance(): Unit
  
  def addRandomTask(): Unit
  
  def newTask(): Unit

  def shutdownApplication(): Unit
  
  def closeInstance(): Unit
}
