package me.timelytask.serviceLayer

import me.timelytask.model.Model

trait ModelService {
  def loadModel(userName: String): Model
  def saveModel(userName: String, model: Model): Unit
}
