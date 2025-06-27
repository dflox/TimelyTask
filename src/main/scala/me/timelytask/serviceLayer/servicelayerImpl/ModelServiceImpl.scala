package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.Model
import me.timelytask.serviceLayer.{ModelService, ServiceModule}

import scala.collection.immutable.HashSet

class ModelServiceImpl(serviceModeule: ServiceModule) extends ModelService {

  override def loadModel(userName: String): Unit = serviceModeule.updateService.updateModel(
    userName,
    this.getModel(userName))
  
  override private[serviceLayer] def getModel(userName: String): Model = {
    Model(
      tasks = serviceModeule.taskService.loadAllTasks(userName).toList,
      tags = HashSet.from(serviceModeule.tagService.getTags(userName)),
      priorities = HashSet.from(serviceModeule.priorityService.getAllPriorities(userName)),
      states = HashSet.from(serviceModeule.taskStateService.getAllTaskStates(userName)),
      config = serviceModeule.configService.getConfig(userName),
      user = serviceModeule.userService.getUser(userName)
    )
  }

  override def saveModel(userName: String, model: Model): Unit = {
    serviceModeule.userService.addUser(userName)
    model.tags.foreach(t => serviceModeule.tagService.addTag(userName, t))
    model.priorities.foreach(p => serviceModeule.priorityService.addPriority(userName, p))
    model.states.foreach(s => serviceModeule.taskStateService.addTaskState(userName, s))
    model.tasks.foreach(t => serviceModeule.taskService.newTask(userName, t))
    serviceModeule.configService.updateConfig(userName, model.config)
  }
}
