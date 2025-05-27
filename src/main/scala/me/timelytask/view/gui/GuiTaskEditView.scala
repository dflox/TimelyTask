package me.timelytask.view.gui

import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.views.*
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane

class GuiTaskEditView(override val render: (Scene, ViewType) => Unit,
                      override val dialogFactory: DialogFactory[Scene],
                      viewTypeCommonsModule: TaskEditCommonsModule)
  extends TaskEditView[Scene]
    with View[TASKEdit, TaskEditViewModel, Scene](viewTypeCommonsModule) {

  private val rootPane = new BorderPane()
  private val scene = new Scene(rootPane)
  currentlyRendered = Some(scene)

  override def update(viewModel: Option[TaskEditViewModel]): Boolean = {
    if viewModel.isEmpty then return false

    TaskEditViewGuiFactory.updateContent(viewModel.get, rootPane)

    render(currentlyRendered.get, TASKEdit)
    true
  }
}