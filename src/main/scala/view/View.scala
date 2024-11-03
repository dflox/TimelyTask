package view

import view.*

trait View {
  def update(viewModel: CalendarModel): String
}
