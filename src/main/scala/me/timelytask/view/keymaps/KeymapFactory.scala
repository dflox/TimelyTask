package me.timelytask.view.keymaps

import me.timelytask.model.settings.{CALENDAR, KeymapConfig, TASKEdit}
import me.timelytask.view.TaskEditViewResolver
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}
import me.timelytask.view.views.{CalendarView, TaskEditView}

object KeymapFactory {
  def createCalendarKeymap(config: KeymapConfig, view: CalendarView): Keymap[CALENDAR, 
    CalendarViewModel, CalendarView] =  Keymap(config, CalendarViewResolver())
  def createTaskEditKeymap(config: KeymapConfig, view: TaskEditView): Keymap[TASKEdit, 
    TaskEditViewModel, TaskEditView] =  Keymap(config, TaskEditViewResolver())
}
