package me.timelytask.view.events.argwrapper

import me.timelytask.model.settings.ViewType
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.viewmodel.viewchanger.ViewChangeArgument

class ViewChangeArgumentWrapper[VT <: ViewType](override val arg: ViewChangeArgument[VT, 
  ViewModel[VT]]) 
  extends ArgWrapper[VT, ViewChangeArgument[VT, ViewModel[VT]]]
