package view

import view.*

trait View {
  def update(model: CalendarModel): String
}
