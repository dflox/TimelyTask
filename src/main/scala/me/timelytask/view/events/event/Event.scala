package me.timelytask.view.events.event

import me.timelytask.model.utility.InputError

trait Event[Args](func: Func[Args],
                  isPossible: Args => Option[InputError],
                  args: Args) {
  def call: Boolean = {
    if (isPossible(args).isEmpty) func(args)
    else false
  }
}
