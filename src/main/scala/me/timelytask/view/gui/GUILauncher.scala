package me.timelytask.view.gui

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ToolBar}
import scalafx.scene.layout.{BorderPane, GridPane, VBox, StackPane, ColumnConstraints, RowConstraints}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Line
import scalafx.scene.text.Text
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import com.github.nscala_time.time.Imports._

object GuiLauncher extends JFXApp3 {

  // Today's date and current time
  var today: DateTime = DateTime.now()
  var daysToShow = 7 // Default to showing one week

  override def start(): Unit = {
    // Main application layout
    stage = new PrimaryStage {
      title = "Calendar View"
      scene = new Scene(800, 600) {
        val rootPane = new BorderPane()

        // Toolbar with buttons at the top
        val toolBar: ToolBar = new ToolBar {
          content = Seq(
            new Button("Day") {
              onAction = _ => {
                daysToShow = 1
                updateCalendar()
              }
            },
            new Button("Week") {
              onAction = _ => {
                daysToShow = 7
                updateCalendar()
              }
            },
            new Button("Month") {
              onAction = _ => {
                daysToShow = 30
                updateCalendar()
              }
            },
            new Button("+") {
              onAction = _ => {
                daysToShow += 1
                updateCalendar()
              }
            },
            new Button("-") {
              onAction = _ => {
                if (daysToShow > 1) daysToShow -= 1
                updateCalendar()
              }
            }
          )
        }
        rootPane.top = toolBar

        // Buttons to navigate days
        val leftButton = new VBox {
          alignment = Pos.Center
          children = new Button("<") {
            onAction = _ => {
              today = today.minusDays(1)
              updateCalendar()
            }
          }
        }
        rootPane.left = leftButton

        val rightButton = new VBox {
          alignment = Pos.Center
          children = new Button(">") {
            onAction = _ => {
              today = today.plusDays(1)
              updateCalendar()
            }
          }
        }
        rootPane.right = rightButton

        // Calendar grid
        val calendarGrid: GridPane = new GridPane {
          hgap = 0
          vgap = 0
          padding = Insets(10)
        }
        rootPane.center = calendarGrid

        def updateCalendar(): Unit = {
          calendarGrid.children.clear()
          calendarGrid.columnConstraints.clear()
          calendarGrid.rowConstraints.clear()

          // Set column and row constraints for resizing
          val timeColumnConstraints = new ColumnConstraints {
            percentWidth = 10
          }
          calendarGrid.columnConstraints.add(timeColumnConstraints)

          for (_ <- 1 to daysToShow) {
            val dayColumnConstraints = new ColumnConstraints {
              percentWidth = 90.0 / daysToShow
            }
            calendarGrid.columnConstraints.add(dayColumnConstraints)
          }

          for (_ <- 0 to 23) {
            val hourRowConstraints = new RowConstraints {
              percentHeight = 100.0 / 24
            }
            calendarGrid.rowConstraints.add(hourRowConstraints)
          }

          // Adding column for time
          for (hour <- 0 to 23) {
            val hourStack = new StackPane {
              children = new Text(f"$hour%02d:00")
              style = "-fx-border-color: lightgray;"
              alignment = Pos.CenterLeft
              maxHeight = Double.MaxValue
            }
            calendarGrid.add(hourStack, 0, hour)
          }

          val dateFormatter = DateTimeFormat.forPattern("EEE, MMM d")

          for (i <- 1 to daysToShow) {
            val date = today.plusDays(i - 1)

            // Add date header
            val dateText = new Text(date.toString(dateFormatter))
            val dateHeader = new StackPane {
              style = if (date.withTimeAtStartOfDay() == DateTime.now().withTimeAtStartOfDay()) "-fx-background-color: rgba(255, 0, 0, 0.2);" else "-fx-background-color: rgba(0, 0, 0, 0.1);"
              children = dateText
            }
            calendarGrid.add(dateHeader, i, 0)

            // Add column for tasks under the date
            for (hour <- 0 to 23) {
              val hourLine = new Line {
                startX = 0
                endX = 1
                stroke = Color.LightGray
                strokeWidth = 1
              }
              val taskCell = new StackPane {
                children = Seq(hourLine) // Single hour line for minimal visual structure
                maxHeight = Double.MaxValue
                maxWidth = Double.MaxValue
                style = if (date.withTimeAtStartOfDay() == DateTime.now().withTimeAtStartOfDay()) "-fx-background-color: rgba(255, 0, 0, 0.05);" else ""
              }
              calendarGrid.add(taskCell, i, hour + 1)
            }
          }

          // Add vertical lines separating days
          for (i <- 1 to daysToShow) {
            val separator = new Line {
              startY = 0
              endY = 600 // Temporary height, adjust dynamically as needed
              stroke = Color.DarkGray
              strokeWidth = 1
            }
            calendarGrid.add(separator, i, 0, 1, 24)
          }

          // Add red line for current time
          val currentTime = DateTime.now()
          val currentHour = currentTime.getHourOfDay
          val currentMinute = currentTime.getMinuteOfHour

          val currentTimeLine = new Line {
            startX = 0
            endX = 800
            stroke = Color.Red
            strokeWidth = 2
          }

          val rowIndex = currentHour + 1
          val rowFraction = currentMinute / 60.0
          currentTimeLine.translateY = (rowFraction * (600.0 / 24))

          calendarGrid.add(currentTimeLine, 1, rowIndex, daysToShow, 1)
        }

        updateCalendar()
        root = rootPane
      }
    }
  }
}
