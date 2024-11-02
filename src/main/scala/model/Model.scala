package model

import java.util.UUID
import scala.collection.immutable.HashSet

case class Model(tasks: List[Task],
            tags: HashSet[Tag],
            priorities: HashSet[Priority],
            states: HashSet[State],
            config: Config) {
}

object Model {
  val default: Model = new Model(List[Task](), HashSet[Tag](), HashSet[Priority](), HashSet[State](), Config.default)
}
