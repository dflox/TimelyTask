package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.Model
import me.timelytask.serviceLayer.{ModelService, ServiceModule}

import scala.collection.immutable.HashSet
import scala.util.Try

class ModelServiceImpl(serviceModule: ServiceModule) extends ModelService {

  override def loadModel(userName: String): Unit = serviceModule.updateService.updateModel(
    userName,
    this.getModel(userName))
  
  override private[serviceLayer] def getModel(userName: String): Model = {
    Model(
      tasks = serviceModule.taskService.loadAllTasks(userName).toList,
      tags = HashSet.from(serviceModule.tagService.getTags(userName)),
      priorities = HashSet.from(serviceModule.priorityService.getAllPriorities(userName)),
      states = HashSet.from(serviceModule.taskStateService.getAllTaskStates(userName)),
      config = serviceModule.configService.getConfig(userName),
      user = serviceModule.userService.getUser(userName)
    )
  }

  override def saveModel(userName: String, model: Model): Unit = {
    serviceModule.updateService.updateModel(userName, model)

    serviceModule.userService.removeUser(userName)

    serviceModule.userService.addUser(userName)
    model.tags.foreach(t => serviceModule.tagService.addTag(userName, t))
    model.priorities.foreach(p => serviceModule.priorityService.addPriority(userName, p))
    model.states.foreach(s => serviceModule.taskStateService.addTaskState(userName, s))
    model.tasks.foreach(t => serviceModule.taskService.newTask(userName, t))
    serviceModule.configService.addConfig(userName, model.config)
  }
}
