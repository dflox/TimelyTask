package me.timelytask.view.events.event

trait Func[Args] {
  def apply(args: Args): Boolean
}