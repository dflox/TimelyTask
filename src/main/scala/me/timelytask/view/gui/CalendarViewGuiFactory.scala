package me.timelytask.view.gui

import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.CalendarCommonsModule
import scalafx.geometry.{HPos, Insets, Pos, VPos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ButtonBar, Label, TextArea}
import scalafx.scene.layout.{BorderPane, ColumnConstraints, GridPane, Pane, RowConstraints, VBox}
import scalafx.scene.text.{Font, FontWeight}

object CalendarViewGuiFactory {
  private val dayNames = Seq("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag")
  private val startTimeHour = 8 // Start um 8 Uhr
  private val endTimeHour = 19  // Ende vor 19 Uhr
  private val numberOfTimeSlots = endTimeHour - startTimeHour

  def updateContent(viewModel: CalendarViewModel, currentScene: Option[Scene], viewTypeCommonsModule: CalendarCommonsModule): Pane = {
    // Hauptlayout erstellen
    val rootPane = new BorderPane()

    // Header mit Titel
    val headerLabel = new Label("Wochenkalender") {
      font = Font.font("Arial", FontWeight.Bold, 20)
    }

    // Navigationsbuttons
    val navBar = new ButtonBar {
      buttons = Seq(
        new Button("Heute"),
        new Button("<< Woche") {
          },
        new Button("< Tag") {
          },
        new Button("> Tag") {
          },
        new Button(">> Woche") {
          }
      )
    }

    val header = new VBox(10) {
      alignment = Pos.Center
      padding = Insets(10)
      children = Seq(headerLabel, navBar)
    }

    // Kalender-Grid erstellen
    val calendarGrid = createCalendarGrid(viewModel)

    // Komponenten im BorderPane anordnen
    rootPane.top = header
    rootPane.center = calendarGrid
    
    rootPane
  }

  private def createCalendarGrid(viewModel: CalendarViewModel): GridPane = {
    val grid = new GridPane {
      hgap = 2
      vgap = 2
      padding = Insets(5)
    }

    // Spalten-Einstellungen
    // Erste Spalte für Zeitbeschriftungen
    grid.columnConstraints.add(new ColumnConstraints {
      halignment = HPos.Right
      minWidth = 50
      prefWidth = 60
    })

    // 7 Spalten für die Wochentage
    dayNames.foreach { _ =>
      grid.columnConstraints.add(new ColumnConstraints {
        minWidth = 80
        prefWidth = 110
        hgrow = scalafx.scene.layout.Priority.Always
      })
    }

    // Zeilen-Einstellungen
    // Erste Zeile für Tagesüberschriften
    grid.rowConstraints.add(new RowConstraints {
      minHeight = 30
      prefHeight = 40
      valignment = VPos.Center
    })

    // Zeilen für Zeitslots
    (0 until numberOfTimeSlots).foreach { _ =>
      grid.rowConstraints.add(new RowConstraints {
        minHeight = 40
        prefHeight = 50
        vgrow = scalafx.scene.layout.Priority.Always
      })
    }

    // Wochentags-Überschriften (Zeile 0, Spalten 1-7)
    dayNames.zipWithIndex.foreach { case (dayName, colIdx) =>
      val dayLabel = new Label(dayName) {
        font = Font.font(null, FontWeight.Bold, 14)
        alignmentInParent = Pos.Center
        maxWidth = Double.MaxValue
      }
      GridPane.setColumnIndex(dayLabel, colIdx + 1)
      GridPane.setRowIndex(dayLabel, 0)
      grid.children.add(dayLabel)
    }

    // Zeitbeschriftungen (Spalte 0, Zeilen 1-N)
    (0 until numberOfTimeSlots).foreach { hourOffset =>
      val hour = startTimeHour + hourOffset
      val timeLabel = new Label(f"$hour%02d:00") {
        padding = Insets(0, 5, 0, 0)
        alignmentInParent = Pos.CenterRight
      }
      GridPane.setColumnIndex(timeLabel, 0)
      GridPane.setRowIndex(timeLabel, hourOffset + 1)
      grid.children.add(timeLabel)
    }

    // TextAreas für die Ereignisse
    for {
      dayCol <- 0 until dayNames.length
      timeRow <- 0 until numberOfTimeSlots
    } {
      val cell = new TextArea {
        prefWidth = 100
        prefHeight = 50
        wrapText = true
        editable = false // Nur Anzeige, keine Bearbeitung
      }
      GridPane.setColumnIndex(cell, dayCol + 1)
      GridPane.setRowIndex(cell, timeRow + 1)
      grid.children.add(cell)
    }

    grid
  }
}