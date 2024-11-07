package me.timelytask.controller

import me.timelytask.model.Model

trait ModelObserver {
  def onModelChange(newModel: Model): Unit
}

class ModelPublisher(private var model: Model) {
  private var observers: List[ModelObserver] = List()

  def subscribe(observer: ModelObserver): Unit = {
    observers = observer :: observers
  }

  def updateModel(newModel: Model): Unit = {
    model = newModel
    observers.foreach(_.onModelChange(model))
  }

  def getCurrentModel: Model = model
}