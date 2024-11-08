package me.timelytask.model

import io.circe.generic.auto.*
import me.timelytask.model.settings.*
import org.jline.keymap.KeyMap

case class Config (keymaps: Map[ViewType, KeyMap[Action]],
                   globalKeymap: KeyMap[Action],
                   defaultStartView: ViewType,
                   defaultDataFileType: FileType,
                   defaultTheme: Theme) {
}

object Config {
  val default: Config = Config(
    keymaps = Map(
      ViewType.CALENDAR -> {
        val keymap = new KeyMap[Action]()
        keymap.bind(NextDay, "\u001B[C")
        keymap.bind(PreviousDay, "\u001B[D")
        keymap.bind(NextWeek, "\u001B[1;5C")
        keymap.bind(PreviousWeek, "\u001B[1;5D")
        keymap.bind(GoToToday, "t")
        keymap
      },
      ViewType.TABLE -> new KeyMap[Action](),
      ViewType.TASK -> new KeyMap[Action](),
      ViewType.KANBAN -> new KeyMap[Action](),
      ViewType.SETTINGS -> new KeyMap[Action]()
    ),
    globalKeymap = new KeyMap[Action](),
    defaultStartView = ViewType.CALENDAR,
    defaultDataFileType = FileType.JSON,
    defaultTheme = Theme.LIGHT
  )
}