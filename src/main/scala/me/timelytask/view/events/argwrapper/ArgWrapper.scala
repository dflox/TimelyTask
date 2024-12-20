package me.timelytask.view.events.argwrapper

import me.timelytask.model.settings.ViewType

trait ArgWrapper[+T <: ViewType, +ArgsType, +Self <: ArgWrapper[T, ArgsType, Self]] {
  def arg: ArgsType
}
