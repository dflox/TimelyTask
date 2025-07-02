//package me.timelytask.view.views.viewImpl.gui
//
//import me.timelytask.model.settings.{TASKEdit, ViewType}
//import me.timelytask.view.viewmodel.TaskEditViewModel
//import me.timelytask.view.views.*
//import me.timelytask.view.views.commonsModules.TaskEditCommonsModule
//import scalafx.scene.Scene
//import scalafx.scene.layout.BorderPane
//
//class GuiTaskEditView(override val render: (Scene, ViewType) => Unit,
//                      override val dialogFactory: DialogFactory[Scene],
//                      viewTypeCommonsModule: TaskEditCommonsModule)
//  extends TaskEditView[Scene]
//    with View[TASKEdit, TaskEditViewModel, Scene](viewTypeCommonsModule) {
//
//  currentlyRendered = None
//
//  override def update(viewModel: Option[TaskEditViewModel]): Boolean = {
//    return false
//    if viewModel.isEmpty then return false
//
//    TaskEditViewGuiFactory.updateContent(viewModel.get, None)
//
//    render(currentlyRendered.get, TASKEdit)
//    true
//  }
//}