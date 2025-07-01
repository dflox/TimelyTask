package me.timelytask.model.config
import io.circe.generic.auto.*
import me.timelytask.model.settings.*
import me.timelytask.model.utility.*

case class Config(keymaps: Map[ViewType, KeymapConfig],
                  globalKeymap: KeymapConfig,
                  startView: ViewType,
                  dataFileType: FileType,
                  theme: Theme)

object Config {
  val default: Config = Config(
    keymaps = Map(
      CALENDAR -> {
        KeymapConfig(
          mappings = Map(
            Key.ShiftRight -> EventTypeId("NextDay"),
            Key.ShiftLeft -> EventTypeId("PreviousDay"),
            Key.CtrlRight -> EventTypeId("NextWeek"),
            Key.CtrlLeft -> EventTypeId("PreviousWeek"),
            Key.T -> EventTypeId("GoToToday"),
            Key.W -> EventTypeId("ShowWholeWeek"),
            Key.Plus -> EventTypeId("ShowMoreDays"),
            Key.Minus -> EventTypeId("ShowLessDays")
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
            Key.MoveDown -> EventTypeId("NextField"),
            Key.MoveUp -> EventTypeId("PreviousField"),
            Key.CtrlS -> EventTypeId("SaveTask")
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
        Key.Z -> EventTypeId("Undo"),
        Key.Y -> EventTypeId("Redo"),
        Key.R -> EventTypeId("AddRandomTask"),
        Key.F4 -> EventTypeId("ShutdownApplication"),
        Key.CtrlG -> EventTypeId("NewGuiWindow"),
        Key.CtrlI -> EventTypeId("NewInstance"),
        Key.CtrlX -> EventTypeId("CloseInstance"),
        Key.N -> EventTypeId("NewTask"),
        Key.G -> EventTypeId("SwitchToView"),
        Key.Num1 -> EventTypeId("ExportToJSON"),
        Key.Num2 -> EventTypeId("ExportToYAML"),
        Key.Num3 -> EventTypeId("ExportToXML"),
        Key.ShiftOne -> EventTypeId("ImportFromJSON"),
        Key.ShiftTwo -> EventTypeId("ImportFromYAML"),
        Key.ShiftThree -> EventTypeId("ImportFromXML"),
      )
    ),
    startView = CALENDAR,
    dataFileType = FileType.JSON,
    theme = Theme.LIGHT
  )
}