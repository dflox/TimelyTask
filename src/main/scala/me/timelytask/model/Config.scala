package me.timelytask.model

import io.circe.generic.auto.*
import me.timelytask.model.settings.*
import me.timelytask.model.utility.*

case class Config(keymaps: Map[ViewType, KeymapConfig],
                  globalKeymap: KeymapConfig,
                  defaultStartView: ViewType,
                  defaultDataFileType: FileType,
                  defaultTheme: Theme) {
}

object Config {
  val default: Config = Config(
    keymaps = Map(
      CALENDAR -> {
        KeymapConfig(
          mappings = Map(
            ShiftRight -> EventTypeId("NextDay"),
            ShiftLeft -> EventTypeId("PreviousDay"),
            CtrlRight -> EventTypeId("NextWeek"),
            CtrlLeft -> EventTypeId("PreviousWeek"),
            T -> EventTypeId("GoToToday"),
            W -> EventTypeId("ShowWholeWeek"),
            Plus -> EventTypeId("ShowMoreDays"),
            Minus -> EventTypeId("ShowLessDays")
          )
        )
      },
      TABLE -> {
        KeymapConfig(
          mappings = Map()
        )
      },
      TASKEdit -> {
        KeymapConfig(
          mappings = Map(
            MoveDown -> EventTypeId("NextField"),
            MoveUp -> EventTypeId("PreviousField"),
            CtrlS -> EventTypeId("SaveTask")
          )
        )
      },
      KANBAN -> {
        KeymapConfig(
          mappings = Map()
        )
      },
      SETTINGS -> {
        KeymapConfig(
          mappings = Map()
        )
      }
    ),
    globalKeymap = KeymapConfig(
      mappings = Map(
        Z -> EventTypeId("Undo"),
        Y -> EventTypeId("Redo"),
        R -> EventTypeId("AddRandomTask"),
        CtrlF4 -> EventTypeId("ShutdownApplication"),
        CtrlG -> EventTypeId("NewGuiWindow"),
        CtrlI -> EventTypeId("NewInstance"),
        CtrlX -> EventTypeId("CloseInstance"),
        N -> EventTypeId("NewTask"),
        G -> EventTypeId("SwitchToView")
      )
    ),
    defaultStartView = CALENDAR,
    defaultDataFileType = FileType.JSON,
    defaultTheme = Theme.LIGHT
  )
}