package me.timelytask.controller.controllersImpl

import me.timelytask.controller.UpdateController
import me.timelytask.model.Model
import me.timelytask.model.config.Config
import me.timelytask.model.priority.Priority
import me.timelytask.model.state.TaskState
import me.timelytask.model.tag.Tag
import me.timelytask.model.task.Task
import me.timelytask.serviceLayer.ServiceModule
import me.timelytask.util.Publisher
import me.timelytask.util.extensions.replaceOne
import me.timelytask.util.extensions.hashSet.replaceOne

class UpdateControllerImpl(serviceModule: ServiceModule, modelPublisher: Publisher[Model]) extends 
                                                                          UpdateController  {
  override def init(): Unit = {
    serviceModule.updateService.registerTagUpdateListener(modelUpdater(_, _, updateTag))
    serviceModule.updateService.registerTaskUpdateListener(modelUpdater(_, _, updateTask))
    serviceModule.updateService.registerPriorityUpdateListener(modelUpdater(_, _, updatePriority))
    serviceModule.updateService.registerConfigUpdateListener(modelUpdater(_, _, updateConfig))
    serviceModule.updateService.registerTaskStateUpdateListener(modelUpdater(_, _, updateTaskState))
    serviceModule.updateService.registerModelUpdateListener(updateModelUpdater)
  }
  
  private def modelUpdater[UpdatedType](userToken: String,
                                        updatedValue: UpdatedType,
                                        modelManipulator: (Model, UpdatedType) => Model)
  : Unit = {
    modelPublisher.getValue(userToken) match {
      case Some(model) =>
        modelPublisher.update(newValue = Some(modelManipulator(model, updatedValue)), target = Some
          (userToken))
      case None =>
        //throw new IllegalArgumentException(s"Model not found for user token: $userToken")
    }
  }
  
  private def updateModelUpdater(userToken: String, updatedModel: Model): Unit = modelPublisher
    .update(newValue = Some(updatedModel), target = Some(userToken))
  
  private def updateTask(currentModel: Model, updatedTask: Task): Model = {
    currentModel.copy(tasks = currentModel.tasks.replaceOne((t: Task) 
        => t.uuid ==updatedTask.uuid, updatedTask))
  }
  
  private def updateTag(currentModel: Model, updatedTag: Tag): Model = {
    currentModel.copy(tags = currentModel.tags.replaceOne((t: Tag) => t.uuid == updatedTag.uuid, updatedTag))
  }
  
  private def updatePriority(currentModel: Model, updatedPriority: Priority): Model = {
    currentModel.copy(priorities = currentModel.priorities.replaceOne(
          (p: Priority) => p.uuid == updatedPriority.uuid, updatedPriority))
  }
  
  private def updateConfig(currentModel: Model, updatedConfig: Config): Model = currentModel.copy(config = updatedConfig)
  
  private def updateTaskState(currentModel: Model, updatedState: TaskState): Model = {
    currentModel.copy(states = currentModel.states.replaceOne(
          (s: TaskState) => s.uuid == updatedState.uuid, updatedState))
  }
}
