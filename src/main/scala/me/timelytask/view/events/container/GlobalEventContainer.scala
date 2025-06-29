package me.timelytask.view.events.container

trait GlobalEventContainer {
  val userToken: String

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
