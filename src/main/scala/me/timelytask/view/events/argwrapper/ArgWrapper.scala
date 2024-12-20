package me.timelytask.view.events.argwrapper

trait ArgWrapper[+T, ArgsType] {
  def arg: ArgsType
}
