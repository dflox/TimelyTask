package me.timelytask.model

import me.timelytask.model.config.Config
import me.timelytask.model.priority.Priority
import me.timelytask.model.state.TaskState
import me.timelytask.model.tag.Tag
import me.timelytask.model.task.Task
import me.timelytask.model.user.User

import scala.collection.immutable.HashSet

case class Model(tasks: List[Task],
                 tags: HashSet[Tag],
                 priorities: HashSet[Priority],
                 states: HashSet[TaskState],
                 config: Config,
                 user: User) {
}

object Model {
  val emptyModel: Model = new Model(List[Task](), HashSet[Tag](), HashSet[Priority](),
    HashSet[TaskState](), Config.default, User.default)
}