package me.timelytask.view.gui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ToolBar}
import scalafx.scene.layout.{BorderPane, GridPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Line
import scalafx.scene.text.Text
import scalafx.scene.layout.StackPane
import scalafx.geometry.Insets
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object CalendarApp extends JFXApp {

  // Today's date and current time
  val today: LocalDate = LocalDate.now()
  val currentTime: LocalTime = LocalTime.now()

  // Main application layout
  stage = new PrimaryStage {
    title = "Calendar View"
    scene = new Scene(800, 600) {
      val rootPane = new BorderPane()

      // Toolbar with buttons at the top
      val toolBar = new ToolBar {
        content = Seq(
          new Button("Day"),
          new Button("Week"),
          new Button("Month"),
          new Button("+"),
          new Button("-")
        )
      }
      rootPane.top = toolBar

      // Calendar grid
      val calendarGrid = new GridPane {
        hgap = 5
        vgap = 5
        padding = Insets(10)
      }

      // Add the top row for dates
      val daysToShow = 7 // For a week view
      val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d")

      // Adding column for time
      val timeColumn = new VBox {
        spacing = 10
        children = (0 to 23).map { hour =>
          new Text(f"$hour%02d:00")
        }
      }
      calendarGrid.add(timeColumn, 0, 1)

      for (i <- 1 to daysToShow) {
        val date = today.plusDays(i - 1)
        val dateText = new Text(date.format(dateFormatter))
        if (date.isEqual(today)) {
          dateText.fill = Color.Red
        }
        val dateCell = new StackPane {
          style = if (date.isEqual(today)) "-fx-background-color: rgba(255, 0, 0, 0.1);" else ""
          children = dateText
        }
        calendarGrid.add(dateCell, i, 0)
      }

      // Adding a horizontal line for the current time
      val currentTimeLine = new Line {
        startX = 0
        endX = 700
        startY = 0
        endY = 0
        stroke = Color.Red
        strokeWidth = 2
      }

      val timeMarker = new StackPane {
        style = "-fx-background-color: rgba(255, 0, 0, 0.1);"
        children = Seq(currentTimeLine)
        padding = Insets((currentTime.getHour * 50) + currentTime.getMinute * 0.83, 0, 0, 0)
      }
      calendarGrid.add(timeMarker, 1, 1)

      rootPane.center = calendarGrid

      root = rootPane
    }
  }
}
