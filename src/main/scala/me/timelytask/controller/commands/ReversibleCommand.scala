package me.timelytask.controller.commands

import me.timelytask.model.*
import me.timelytask.util.publisher.PublisherImpl

trait StoreHandler[Args, StateValue] {
  def apply(args: Args): Option[StateValue]
}

// TODO: Rename to something like absolute state manipulating command
trait RestoreHandler[Args, StateValue] {
  def apply(args: Args, value: StateValue): Boolean
}

trait ReversibleCommand[Args, StateValue](handler: Handler[Args], args: Args,
                                          storeHandler: StoreHandler[Args, StateValue],
                                          restoreHandler: RestoreHandler[Args, StateValue])
  extends Command[Args] {
  private var state: Option[StateValue] = None

  override def execute: Boolean = {
    state match {
      case Some(s) => false
      case None =>
        state = storeHandler(args)
        handler(args)
    }
  }

  override def redo: Boolean = {
    state match {
      case Some(s) => handler(args)
      case None => false
    }
  }

  override def undo: Boolean = {
    state match {
      case Some(s) => restoreHandler(args, s)
      case None => false
    }
  }
}

trait ReversibleCommandCompanion[StateValue, T <: ReversibleCommand[Args, StateValue], Args] {
  protected var handler: Option[Handler[Args]] = None
  protected var storeHandler: Option[StoreHandler[Args, StateValue]] = None
  protected var restoreHandler: Option[RestoreHandler[Args, StateValue]] = None

  def setHandler(newHandler: Handler[Args], newStoreHandler: StoreHandler[Args, StateValue],
                 newRestoreHandler: RestoreHandler[Args, StateValue]): Boolean = {
    handler = Some(newHandler)
    storeHandler = Some(newStoreHandler)
    restoreHandler = Some(newRestoreHandler)
    true
  }

  def createCommand(args: Args): T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create(args)
  }

  protected def create(args: Args): T
}