package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.DateTime

trait ViewModel {
  val today: DateTime = DateTime.now()
}
