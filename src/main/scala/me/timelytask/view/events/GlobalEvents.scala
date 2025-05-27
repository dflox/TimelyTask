//package me.timelytask.view.events
//
//import me.timelytask.model.settings.ViewType
//import me.timelytask.model.utility.InputError
//import me.timelytask.view.events.argwrapper.ViewChangeArgumentWrapper
//import me.timelytask.view.viewmodel.ViewModel
//import me.timelytask.view.viewmodel.viewchanger.ViewChangeArgument
//
//import java.util.UUID
//
//case class Undo(handler: Handler[Unit],
//                isPossible: Unit => Option[InputError])
//  extends Event[Unit](handler, isPossible)
//
//case object Undo extends EventCompanion[Undo, Unit] {
//  override protected def create: Undo = Undo(handler.get, isPossible.get)
//}
//
//case class Redo(handler: Handler[Unit],
//                isPossible: Unit => Option[InputError])
//  extends Event[Unit](handler, isPossible)
//
//case object Redo extends EventCompanion[Redo, Unit] {
//  override protected def create: Redo = Redo(handler.get, isPossible.get)
//}
//
//case class Exit(handler: Handler[Unit],
//                isPossible: Unit => Option[InputError])
//  extends Event[Unit](handler, isPossible)
//
//case object Exit extends EventCompanion[Exit, Unit] {
//  override protected def create: Exit = Exit(handler.get, isPossible.get)
//}
//
//case class Save(handler: Handler[Unit],
//                isPossible: Unit => Option[InputError])
//  extends Event[Unit](handler, isPossible)
//
//case object Save extends EventCompanion[Save, Unit] {
//  override protected def create: Save = Save(handler.get, isPossible.get)
//}
//
//case class SaveAndExit(handler: Handler[Unit],
//                       isPossible: Unit => Option[InputError])
//  extends Event[Unit](handler, isPossible)
//
//case object SaveAndExit extends EventCompanion[SaveAndExit, Unit] {
//  override protected def create: SaveAndExit = SaveAndExit(handler.get, isPossible.get)
//}
//
//case class AddTask(handler: Handler[Unit],
//                   isPossible: Unit => Option[InputError])
//  extends Event[Unit](handler, isPossible)
//
//case object AddTask extends EventCompanion[AddTask, Unit] {
//  override protected def create: AddTask = AddTask(handler.get, isPossible.get)
//}
//
//case class RemoveTask(handler: Handler[UUID],
//                      isPossible: UUID => Option[InputError])
//  extends Event[UUID](handler, isPossible)
//
//case object RemoveTask extends EventCompanion[RemoveTask, UUID] {
//  override protected def create: RemoveTask = RemoveTask(handler.get, isPossible.get)
//}
//
//case class EditTask(handler: Handler[UUID],
//                    isPossible: UUID => Option[InputError])
//  extends Event[UUID](handler, isPossible)
//
//case object EditTask extends EventCompanion[EditTask, UUID] {
//  override protected def create: EditTask = EditTask(handler.get, isPossible.get)
//}
//
//case class AddTag(handler: Handler[Unit],
//                  isPossible: Unit => Option[InputError])
//  extends Event[Unit](handler, isPossible)
//
//case object AddTag extends EventCompanion[AddTag, Unit] {
//  override protected def create: AddTag = AddTag(handler.get, isPossible.get)
//}
//
//case class RemoveTag(handler: Handler[UUID],
//                     isPossible: UUID => Option[InputError])
//  extends Event[UUID](handler, isPossible)
//
//case object RemoveTag extends EventCompanion[RemoveTag, UUID] {
//  override protected def create: RemoveTag = RemoveTag(handler.get, isPossible.get)
//}
//
//case class EditTag(handler: Handler[UUID],
//                   isPossible: UUID => Option[InputError])
//  extends Event[UUID](handler, isPossible)
//
//case object EditTag extends EventCompanion[EditTag, UUID] {
//  override protected def create: EditTag = EditTag(handler.get, isPossible.get)
//}
//
//case class AddPriority(handler: Handler[Unit],
//                       isPossible: Unit => Option[InputError])
//  extends Event[Unit](handler, isPossible)
//
//case object AddPriority extends EventCompanion[AddPriority, Unit] {
//  override protected def create: AddPriority = AddPriority(handler.get, isPossible.get)
//}
//
//case class RemovePriority(handler: Handler[UUID],
//                          isPossible: UUID => Option[InputError])
//  extends Event[UUID](handler, isPossible)
//
//case object RemovePriority extends EventCompanion[RemovePriority, UUID] {
//  override protected def create: RemovePriority = RemovePriority(handler.get, isPossible.get)
//}
//
//case class EditPriority(handler: Handler[UUID],
//                        isPossible: UUID => Option[InputError])
//  extends Event[UUID](handler, isPossible)
//
//case object EditPriority extends EventCompanion[EditPriority, UUID] {
//  override protected def create: EditPriority = EditPriority(handler.get, isPossible.get)
//}
//
//case class AddState(handler: Handler[Unit],
//                    isPossible: Unit => Option[InputError])
//  extends Event[Unit](handler, isPossible)
//
//case object AddState extends EventCompanion[AddState, Unit] {
//  override protected def create: AddState = AddState(handler.get, isPossible.get)
//}
//
//case class RemoveState(handler: Handler[UUID],
//                       isPossible: UUID => Option[InputError])
//  extends Event[UUID](handler, isPossible)
//
//case object RemoveState extends EventCompanion[RemoveState, UUID] {
//  override protected def create: RemoveState = RemoveState(handler.get, isPossible.get)
//}
//
//case class EditState(handler: Handler[UUID],
//                     isPossible: UUID => Option[InputError])
//  extends Event[UUID](handler, isPossible)
//
//case object EditState extends EventCompanion[EditState, UUID] {
//  override protected def create: EditState = EditState(handler.get, isPossible.get)
//}
//
////case class ChangeView(handlers: List[TypeSensitiveHandler[?,
////  ViewChangeArgument[ViewType, ViewModel[ViewType]], ViewChangeArgumentWrapper[ViewType]]],
////                      isPossibles: List[ViewChangeArgumentWrapper[ViewType] => 
////                      Option[InputError]])
////  extends MultiHandlerEvent[ViewChangeArgument[ViewType, ViewModel[ViewType]],
////    ViewChangeArgumentWrapper[ViewType]](handlers, isPossibles)
////case object ChangeView extends MultiHandlerEventCompanion[ViewChangeArgument[ViewType,
////  ViewModel[ViewType]], ViewChangeArgumentWrapper[ViewType], ChangeView] {
////  override protected def create: ChangeView = ChangeView(handlers, isPossibles)
////}
//
////case class ChangeView(handlers: List[TypeSensitiveHandler[?,
////  ViewChangeArgument[ViewType, ViewModel[ViewType, ?]], ViewChangeArgumentWrapper[ViewType, 
////  ViewModel[ViewType, ?]]]],
////                      isPossibles: List[ViewChangeArgumentWrapper[ViewType, ViewModel[ViewType,
////                      ?]] => Option[InputError]])
////  extends MultiHandlerEvent[ViewChangeArgument[ViewType, ViewModel[ViewType, ?]],
////    ViewChangeArgumentWrapper[ViewType, ViewModel[ViewType, ?]]](handlers, isPossibles)
////
////case object ChangeView extends MultiHandlerEventCompanion[ViewChangeArgument[ViewType,
////  ViewModel[ViewType, ?]], ViewChangeArgumentWrapper[ViewType, ViewModel[ViewType, ?]], 
////  ChangeView] {
////  override protected def create: ChangeView = ChangeView(handlers, isPossibles)
////}
//
//case class ChangeView(handlers: List[TypeSensitiveHandler[?,
//  ViewChangeArgument[ViewType, ViewModel[ViewType, ?]],
//  ViewChangeArgumentWrapper[ViewType, ViewModel[ViewType, ?], ?]]])
//  extends MultiHandlerEvent[
//    ViewChangeArgument[ViewType, ViewModel[ViewType, ?]],
//    ViewChangeArgumentWrapper[ViewType, ViewModel[ViewType, ?], ?]](handlers)
//
//case object ChangeView extends MultiHandlerEventCompanion[
//  ViewChangeArgument[ViewType, ViewModel[ViewType, ?]],
//  ViewChangeArgumentWrapper[ViewType, ViewModel[ViewType, ?], ?],
//  ChangeView] {
//  override protected def create: ChangeView = ChangeView(handlers)
//}
//
////case class ChangeTheme() extends Event[]
//
////case class ChangeDataFileType() extends Event
//
////case class ChangeDataFilePath() extends Event
