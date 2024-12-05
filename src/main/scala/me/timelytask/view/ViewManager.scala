package me.timelytask.view

import me.timelytask.view.events.NextDay
import me.timelytask.view.viewmodel.ViewModel

trait ViewManager {
  def update(viewModel: ViewModel): Boolean
  
  val nextDay: NextDay = NextDay.createEvent()
  
}
