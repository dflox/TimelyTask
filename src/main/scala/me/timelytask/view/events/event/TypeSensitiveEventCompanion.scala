package me.timelytask.view.events.event

import me.timelytask.model.utility.InputError

import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

trait TypeSensitiveEventCompanion[EventType <: Event[Args], Args] {
  private var wrappers: List[TypeSensitiveWrapper[?, Args]] = Nil

  def addHandler[T: ClassTag](newHandler: Func[Args], isPossible: Args => Option[InputError])
  : Unit = {
    wrappers = TypeSensitiveWrapper[T, Args](newHandler, isPossible) :: wrappers
  }

  private class TypeSensitiveWrapper[T: ClassTag, Args](val handler: Func[Args],
                                                        val isPossible: Args => Option[InputError]
                                                         ) {
    def matchesType[A: ClassTag]: Boolean =
      implicitly[ClassTag[T]].runtimeClass == implicitly[ClassTag[A]].runtimeClass
  }

  def createEvent[T: ClassTag]: EventType = {
    Try[EventType] {
      wrappers.find(_.matchesType[T]) match {
        case Some(wrapper) => create[T](wrapper.handler, wrapper.isPossible)
        case None => throw new Exception(
          s"Handler of requested type is not set for companion object")
      }
    } match {
      case Success(event) => event
      case Failure(ex) => throw ex
    }
  }

  protected def create[T: ClassTag](handler: Func[Args], isPossible: Args => Option[InputError])
  : EventType
}
