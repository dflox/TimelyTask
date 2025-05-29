  package me.timelytask.view.gui

  import me.timelytask.view.viewmodel.CalendarViewModel
  import me.timelytask.view.views.CalendarCommonsModule
  import scalafx.geometry.{HPos, Insets, Pos, VPos}
  import scalafx.scene.Scene
  import scalafx.scene.control.{Button, ButtonBar, Label, TextArea}
  import scalafx.scene.layout.{BorderPane, ColumnConstraints, GridPane, Pane, RowConstraints, VBox}
  import scalafx.scene.paint.Color
  import scalafx.scene.text.{Font, FontWeight}

  import java.time.LocalDate
  import java.time.DayOfWeek
  import java.time.format.DateTimeFormatter
  import java.util.Locale

  object CalendarViewGuiFactory {
    private val dayAbbreviations = Seq("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So")
    private val startTimeHour = 8
    private val endTimeHour = 19
    private val numberOfTimeSlots = endTimeHour - startTimeHour

    private val germanLocale = new Locale("de", "DE")
    private val dayMonthFormatter = DateTimeFormatter.ofPattern("dd.MM", germanLocale)
    private val dayFormatter = DateTimeFormatter.ofPattern("dd.", germanLocale)
    private val dayMonthYearFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", germanLocale)

    def updateContent(
                       viewModel: CalendarViewModel,
                       currentScene: Option[Scene],
                       viewTypeCommonsModule: CalendarCommonsModule
                     ): Pane = {
      val rootPane = new BorderPane()

      // --- Date Calculations for Display ---
      // This factory currently always shows the week of "today" for its own labels.
      // The ViewModel's date state would be used if the navigation buttons
      // were to update a central ViewModel which then caused this factory to be called
      // with a specific target date from that ViewModel.
      val today = LocalDate.now()
      val firstDayOfDisplayedWeek = today.`with`(DayOfWeek.MONDAY)

      val lastDayOfDisplayedWeek = firstDayOfDisplayedWeek.plusDays(6)

      val startDayText = firstDayOfDisplayedWeek.format(dayFormatter)
      val endDayMonthYearText = lastDayOfDisplayedWeek.format(dayMonthYearFormatter)
      val dateSpanString = s"${startDayText} - ${endDayMonthYearText}"

      // --- Header Elements ---
      val headerLabel = new Label("Wochenkalender") {
        font = Font.font("Arial", FontWeight.Bold, 20)
      }

      val dateSpanLabel = new Label(dateSpanString) {
        font = Font.font("Arial", FontWeight.Normal, 14)
        textFill = Color.Grey
      }

      // --- Navigationsbuttons mit Event-Handlern ---
      val todayBtn = new Button("Heute") {
        onAction = _ => {
          // TODO: Implement viewTypeCommonsModule.eventContainer.goToToday()
          // This method should update the ViewModel to the current week,
          // and then trigger a UI refresh by recalling updateContent.
          println("Heute button clicked - goToToday action pending implementation")
          // For this version of the factory, goToToday() would cause a refresh,
          // and this factory would re-calculate based on LocalDate.now().
          // If the ViewModel were the source of truth for displayed dates,
          // goToToday would update the VM, and then the factory would use VM's date.
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

      // --- New Button for "Neue Task" ---
      val newTaskBtn = new Button("Neue Task") {
        onAction = _ => {
          // switch to TaskEditViewGui
        }
      }

      val navBar = new ButtonBar {
        // Add the new button to the sequence
        buttons = Seq(
          todayBtn,
          prevWeekBtn,
          prevDayBtn,
          nextDayBtn,
          nextWeekBtn,
          newTaskBtn // Added here
        )
      }

      val header = new VBox(5) {
        alignment = Pos.Center
        padding = Insets(10)
        children = Seq(headerLabel, dateSpanLabel, navBar)
      }

      val calendarGrid = createCalendarGrid(viewModel, firstDayOfDisplayedWeek)

      rootPane.top = header
      rootPane.center = calendarGrid
      rootPane
    }

    private def createCalendarGrid(viewModel: CalendarViewModel, firstDayOfDisplayedWeek: LocalDate): GridPane = {
      val grid = new GridPane {
        hgap = 2; vgap = 2; padding = Insets(5)
      }

      grid.columnConstraints.add(new ColumnConstraints {
        halignment = HPos.Right; minWidth = 50; prefWidth = 60
      })
      dayAbbreviations.foreach { _ =>
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

      val weekDates: Seq[LocalDate] = (0 until 7).map(i => firstDayOfDisplayedWeek.plusDays(i))

      dayAbbreviations.zip(weekDates).zipWithIndex.foreach { case ((dayAbbrev, date), colIdx) =>
        val dayDateText = s"$dayAbbrev. ${date.format(dayMonthFormatter)}"
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