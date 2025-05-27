//package me.timelytask.view.events
//
//import me.timelytask.model.Task
//import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
//import me.timelytask.model.utility.InputError
//import me.timelytask.view.viewmodel.elemts.{FocusDirection, Focusable}
//
//import scala.reflect.ClassTag
//
//// Focus Events
//
//case class MoveFocusCalendarView(handler: Handler[FocusDirection],
//                                 isPossible: FocusDirection => Option[InputError])
//  extends Event[FocusDirection](handler, isPossible)
//
//case class MoveFocusTaskEditView(handler: Handler[FocusDirection],
//                                 isPossible: FocusDirection => Option[InputError])
//  extends Event[FocusDirection](handler, isPossible)
//
//case object MoveFocus extends TypeSensitiveEventCompanion[Event[FocusDirection], FocusDirection] {
//  override protected def create[T: ClassTag](handler: Handler[FocusDirection],
//                                             isPossible: FocusDirection => Option[InputError])
//  : Event[FocusDirection] = {
//    val tClass = implicitly[ClassTag[T]].runtimeClass
//    tClass match {
//      case c if c == classOf[CALENDAR] => MoveFocusCalendarView(handler, isPossible)
//      case c if c == classOf[TASKEdit] => MoveFocusTaskEditView(handler, isPossible)
//      case _ => throw new IllegalArgumentException(s"Unsupported type: ${tClass.getName}")
//    }
//  }
//}
//
//case class SetFocusToTaskCalendarView(handler: Handler[Task],
//                                      isPossible: Task => Option[InputError])
//  extends Event[Task](handler, isPossible)
//
//case class SetFocusToTaskTaskEditView(handler: Handler[Task],
//                                      isPossible: Task => Option[InputError])
//  extends Event[Task](handler, isPossible)
//
//case object SetFocusTo extends TypeSensitiveEventCompanion[Event[Task], Task] {
//  override protected def create[T: ClassTag](handler: Handler[Task],
//                                             isPossible: Task => Option[InputError])
//  : Event[Task] = {
//    val tClass = implicitly[ClassTag[T]].runtimeClass
//    tClass match {
//      case c if c == classOf[CALENDAR] => SetFocusToTaskCalendarView(handler, isPossible)
//      case c if c == classOf[TASKEdit] => SetFocusToTaskTaskEditView(handler, isPossible)
//      case _ => throw new IllegalArgumentException(s"Unsupported type: ${tClass.getName}")
//    }
//  }
//}