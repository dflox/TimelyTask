package me.timelytask.model

import io.circe.generic.auto.*
import me.timelytask.model.settings.*
import me.timelytask.view.events.Event
import org.jline.keymap.KeyMap

case class Config(keymaps: Map[ViewType, KeyMap[Event]],
                  globalKeymap: KeyMap[Event],
                  defaultStartView: ViewType,
                  defaultDataFileType: FileType,
                  defaultTheme: Theme) {
}

object Config {
  val default: Config = Config(
    keymaps = Map(
      ViewType.CALENDAR -> {
        val keymap = new KeyMap[Event]()
        keymap.bind(NextDay, "\u001B[C")
        keymap.bind(PreviousDay, "\u001B[D")
        keymap.bind(NextWeek, "\u001B[1;5C")
        keymap.bind(PreviousWeek, "\u001B[1;5D")
        keymap.bind(GoToToday, "t")
        keymap
      },
      ViewType.TABLE -> new KeyMap[Event](),
      ViewType.TASK -> new KeyMap[Event](),
      ViewType.KANBAN -> new KeyMap[Event](),
      ViewType.SETTINGS -> new KeyMap[Event]()
    ),
    globalKeymap = new KeyMap[Event](),
    defaultStartView = ViewType.CALENDAR,
    defaultDataFileType = FileType.JSON,
    defaultTheme = Theme.LIGHT
  )
}