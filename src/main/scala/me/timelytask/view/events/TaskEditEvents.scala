package me.timelytask.view.events

import me.timelytask.model.Task

case class SaveTask() extends Event[Task]
case object SaveTask extends EventCompanion[SaveTask, Task]