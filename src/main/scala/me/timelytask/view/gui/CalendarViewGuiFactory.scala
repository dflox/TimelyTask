package me.timelytask.view.gui

import com.github.nscala_time.time.Imports.{DateTime, Interval, Period}
import me.timelytask.model.utility.TimeSelection
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.CalendarCommonsModule
import scalafx.geometry.{HPos, Insets, Pos, VPos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ButtonBar, Label, TextArea}
import scalafx.scene.layout.{BorderPane, ColumnConstraints, GridPane, Pane, RowConstraints, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight}

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

object CalendarViewGuiFactory {

  private val startTimeHour = 8
  private val endTimeHour = 19
  private val numberOfTimeSlots = endTimeHour - startTimeHour

  private val germanLocale = new Locale("de", "DE")
  private val dayMonthFormatter = DateTimeFormatter.ofPattern("dd.MM", germanLocale)
  private val dayFormatter = DateTimeFormatter.ofPattern("dd.", germanLocale)
  private val dayMonthYearFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", germanLocale)

  private def toJavaLocalDate(dateTime: DateTime): LocalDate = {
    LocalDate.of(dateTime.getYear, dateTime.getMonthOfYear, dateTime.getDayOfMonth)
  }

  def updateContent(
                     viewModel: CalendarViewModel,
                     currentScene: Option[Scene],
                     viewTypeCommonsModule: CalendarCommonsModule
                   ): Pane = {
    val rootPane = new BorderPane()

    val timeSelection: TimeSelection = viewModel.timeSelection
    val daySpanList: List[DateTime] = timeSelection.getDaySpan

    val (_, _, dateSpanStringToDisplay) = {
      if (daySpanList.nonEmpty) {
        val firstD = toJavaLocalDate(daySpanList.head)
        val lastD = toJavaLocalDate(daySpanList.last)

        val startDayText = firstD.format(dayFormatter)
        val endDayMonthYearText = lastD.format(dayMonthYearFormatter)
        (firstD, lastD, s"${startDayText} - ${endDayMonthYearText}")
      } else {
        System.err.println("CalendarViewGuiFactory: timeSelection.getDaySpan is empty. Displaying empty date range.")
        (LocalDate.now(), LocalDate.now(), "Keine Daten - Keine Daten")
      }
    }

    val headerLabel = new Label("Wochenkalender") {
      font = Font.font("Arial", FontWeight.Bold, 20)
    }

    val dateSpanLabel = new Label(dateSpanStringToDisplay) {
      font = Font.font("Arial", FontWeight.Normal, 14)
      textFill = Color.Grey
    }

    val todayBtn = new Button("Heute") {
      onAction = _ => {
        println("Heute button clicked - goToToday action pending implementation")
      }
    }
    val prevWeekBtn = new Button("<< Woche") {
      onAction = _ => viewTypeCommonsModule.eventContainer.previousWeek()
    }
    val prevDayBtn = new Button("< Tag") {
      onAction = _ => viewTypeCommonsModule.eventContainer.previousDay()
    }
    val nextDayBtn = new Button("> Tag") {
      onAction = _ => viewTypeCommonsModule.eventContainer.nextDay()
    }
    val nextWeekBtn = new Button(">> Woche") {
      onAction = _ => viewTypeCommonsModule.eventContainer.nextWeek()
    }

    val newTaskBtn = new Button("Neue Task") {
      onAction = _ => {
        // switch to TaskEditViewGui
      }
    }

    val navBar = new ButtonBar {
      buttons = Seq(
        todayBtn,
        prevWeekBtn,
        prevDayBtn,
        nextDayBtn,
        nextWeekBtn,
        newTaskBtn
      )
    }

    val header = new VBox(5) {
      alignment = Pos.Center
      padding = Insets(10)
      children = Seq(headerLabel, dateSpanLabel, navBar)
    }

    val calendarGrid = createCalendarGrid(viewModel)

    rootPane.top = header
    rootPane.center = calendarGrid
    rootPane
  }

  private def createCalendarGrid(viewModel: CalendarViewModel): GridPane = {
    val grid = new GridPane {
      hgap = 2; vgap = 2; padding = Insets(5)
    }

    val weekDates: Seq[LocalDate] = viewModel.timeSelection.getDaySpan.map(toJavaLocalDate)

    grid.columnConstraints.add(new ColumnConstraints {
      halignment = HPos.Right; minWidth = 50; prefWidth = 60
    })

    (0 until weekDates.length).foreach { _ =>
      grid.columnConstraints.add(new ColumnConstraints {
        minWidth = 80; prefWidth = 110; hgrow = scalafx.scene.layout.Priority.Always
      })
    }

    grid.rowConstraints.add(new RowConstraints {
      minHeight = 30; prefHeight = 40; valignment = VPos.Center
    })
    (0 until numberOfTimeSlots).foreach { _ =>
      grid.rowConstraints.add(new RowConstraints {
        minHeight = 40; prefHeight = 50; vgrow = scalafx.scene.layout.Priority.Always
      })
    }

    if (weekDates.isEmpty) {
      System.err.println("CalendarViewGuiFactory: No dates from timeSelection to display in grid.")
    }

    weekDates.zipWithIndex.foreach { case (date, colIdx) =>
      val dayOfWeekShortName = date.getDayOfWeek.getDisplayName(TextStyle.SHORT, germanLocale)
      val dayDateText = s"$dayOfWeekShortName ${date.format(dayMonthFormatter)}"

      val dayLabel = new Label(dayDateText) {
        font = Font.font(null, FontWeight.Bold, 14); alignmentInParent = Pos.Center; maxWidth = Double.MaxValue
      }
      GridPane.setColumnIndex(dayLabel, colIdx + 1); GridPane.setRowIndex(dayLabel, 0)
      grid.children.add(dayLabel)
    }

    (0 until numberOfTimeSlots).foreach { hourOffset =>
      val hour = startTimeHour + hourOffset
      val timeLabel = new Label(f"$hour%02d:00") {
        padding = Insets(0, 5, 0, 0); alignmentInParent = Pos.CenterRight; font = Font.font(null, FontWeight.Normal, 12)
      }
      GridPane.setColumnIndex(timeLabel, 0); GridPane.setRowIndex(timeLabel, hourOffset + 1)
      grid.children.add(timeLabel)
    }

    for {
      (date, dayCol) <- weekDates.zipWithIndex
      timeRow <- 0 until numberOfTimeSlots
    } {
      val cell = new TextArea {
        wrapText = true; editable = false
      }
      GridPane.setColumnIndex(cell, dayCol + 1); GridPane.setRowIndex(cell, timeRow + 1)
      grid.children.add(cell)
    }
    grid
  }
}