package me.timelytask.model

import me.timelytask.model.state.TaskState

import java.util.UUID
import scala.collection.immutable.HashSet

case class Model(tasks: List[Task],
                 tags: HashSet[Tag],
                 priorities: HashSet[Priority],
                 states: HashSet[TaskState],
                 config: Config) {
}

object Model {
  val default: Model = new Model(List[Task](), HashSet[Tag](), HashSet[Priority](),
    HashSet[TaskState](), Config.default)
}