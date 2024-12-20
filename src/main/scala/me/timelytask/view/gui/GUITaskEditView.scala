package me.timelytask.view.gui

import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.view.views.{CalendarView, DialogFactory}
import scalafx.scene.Scene
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.gui.TaskEditViewSceneFactory

class GUITaskEditView(override val renderer: (Scene, ViewType) => Unit)
                     (using
                      override val viewModelPublisher: Publisher[TaskEditViewModel],
                      override val dialogFactory: DialogFactory[Scene]) 
  extends CalendarView[Scene] {
  override def update(viewModel: Option[TaskEditViewModel]): Boolean = {
    if viewModel.isEmpty then return false
    currentlyRendered = Some(TaskEditViewSceneFactory.createTaskDetailsScene())
    renderer(currentlyRendered.get, TASKEdit)
    true
  }

}
