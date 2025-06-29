package me.timelytask.controller.commands

@FunctionalInterface
trait Handler[Args] {
  def apply(args: Args): Boolean
}

trait Command[Args] {
  def execute: Boolean

  def redo: Boolean

  def undo: Boolean
}