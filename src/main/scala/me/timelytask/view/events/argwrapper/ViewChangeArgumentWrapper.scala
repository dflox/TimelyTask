package me.timelytask.view.events.argwrapper

import me.timelytask.model.settings.ViewType
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.viewmodel.viewchanger.ViewChangeArgument

class ViewChangeArgumentWrapper[+VT <: ViewType, +VM <: ViewModel[VT, VM], VCA
<: ViewChangeArgument[VT, VM]](
                                override val arg: VCA)
  extends ArgWrapper[VT, ViewChangeArgument[VT, VM], ViewChangeArgumentWrapper[VT, VM, VCA]] 
