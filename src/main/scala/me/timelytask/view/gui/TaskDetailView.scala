package me.timelytask.view.gui

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, VBox, HBox}
import scalafx.scene.text.Text
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import scalafx.scene.control.{ScrollPane, TableView, TableColumn}
import scalafx.stage.Screen
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.{GridPane, ColumnConstraints, RowConstraints}
import scalafx.scene.paint.Color
import scalafx.scene.text.{Text, Font}
import scalafx.geometry.Pos

object TaskDetailsView {

  def createTaskDetailsScene(): Scene = {
    val screenWidth = Screen.primary.visualBounds.width
    val screenHeight = Screen.primary.visualBounds.height

    new Scene(screenWidth, screenHeight) {
      val rootPane = new BorderPane()

      val name = "Task Name"
      val description = "This is an example of a very long task description that goes into great detail about the task at hand. The task involves multiple steps and requires a thorough understanding of the underlying processes."
      val state = "In Progress"
      val priority = "High"
      val tags = "Work, Urgent"

      val header = new VBox {
        spacing = 10
        alignment = Pos.Center
        padding = Insets(20)
        children = Seq(
          new Text(name) {
            style = "-fx-font-size: 20; -fx-font-weight: bold; -fx-text-alignment: center;"
          },
          new Text("Details") {
            style = "-fx-font-size: 20; -fx-font-weight: bold;"
          }
        )
      }

      val detailsTable = createTable(Seq(
        ("Description", description),
        ("State", state),
        ("Priority", priority),
        ("Tags", tags)
      ))

      val datesTable = createTable(Seq(
        ("Deadline", "2024-12-20"),
        ("Schedule Date", "2024-12-15"),
        ("Recurrence Interval", "Weekly")
      ))

      val tablesBox = new HBox {
        spacing = 20
        alignment = Pos.Center
        padding = Insets(20)
        children = Seq(
          new VBox {
            alignment = Pos.Center
            hgrow = scalafx.scene.layout.Priority.Always
            children = Seq(detailsTable)
            style = "-fx-pref-width: 50%;"
          },
          new VBox {
            alignment = Pos.Center
            hgrow = scalafx.scene.layout.Priority.Always
            children = Seq(datesTable)
            style = "-fx-pref-width: 50%;"
          }
        )
      }

      val scrollPaneContent = new VBox {
        spacing = 20
        alignment = Pos.Center
        padding = Insets(20)
        children = Seq(
          header,
          tablesBox
        )
      }

      val scrollPane = new ScrollPane {
        content = scrollPaneContent
      }

      rootPane.center = scrollPane
      root = rootPane
    }
  }

  private def createTable(entries: Seq[(String, String)]): VBox = {
    import scalafx.scene.layout.{GridPane, ColumnConstraints, RowConstraints}
    import scalafx.scene.paint.Color
    import scalafx.scene.text.{Text, Font}
    import scalafx.geometry.Pos

    val grid = new GridPane {
      hgap = 0
      vgap = 0
      alignment = Pos.Center
      columnConstraints.addAll(
        new ColumnConstraints { percentWidth = 50 },
        new ColumnConstraints { percentWidth = 50 }
      )
    }

    entries.zipWithIndex.foreach { case ((left, right), rowIndex) =>
      // Create the left cell with a light grey background
      val leftCell = new VBox {
        alignment = Pos.Center
        style = "-fx-background-color: lightgrey; -fx-border-color: lightgrey; -fx-border-width: 1px;"
        children = Seq(new Text(left) {
          font = Font.font("Arial", 16)
          style = "-fx-font-weight: bold;"
          wrappingWidth = 200 // Adjust width for multiline text if necessary
        })
      }

      // Create the right cell
      val rightCell = new VBox {
        alignment = Pos.Center
        style = "-fx-border-color: lightgrey; -fx-border-width: 1px;"
        children = Seq(new Text(right) {
          font = Font.font("Arial", 16)
          style = "-fx-font-weight: bold;"
          wrappingWidth = 200 // Adjust width for multiline text if necessary
        })
      }

      // Add cells to the grid
      grid.add(leftCell, 0, rowIndex)
      grid.add(rightCell, 1, rowIndex)
    }

    new VBox {
      alignment = Pos.Center
      children = Seq(grid)
    }
  }

}