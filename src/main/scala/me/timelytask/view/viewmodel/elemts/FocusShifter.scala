package me.timelytask.view.viewmodel.elemts

trait FocusShifter {
  def moveFocus(direction: FocusDirection): Option[FocusShifter]
}
