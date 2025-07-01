package me.timelytask.serviceLayer

import me.timelytask.model.Model

trait ModelService {
  def loadModel(userName: String): Unit
  def loadModelOrCreate(userName: String): Unit
  private[serviceLayer] def getModel(userName: String): Model
  def saveModel(userName: String, model: Model): Unit
}
