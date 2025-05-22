package me.timelytask.controller

trait PersistenceController {
  
  private[controller] def init(): Unit
  
  private[controller] def loadModelFromDB(): Unit
  
  def SaveModelTo(serializationType: String): Unit

  def LoadModel(serializationType: String): Unit
}
