package me.timelytask.view.gui

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.scene.text.Text
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import scalafx.scene.control.{ScrollPane, TableView, TableColumn}
import scalafx.stage.Screen
import scalafx.collections.ObservableBuffer

object TaskDetailsView {

  def createTaskDetailsScene(): Scene = {
    val screenWidth = Screen.primary.visualBounds.width
    val screenHeight = Screen.primary.visualBounds.height

    new Scene(screenWidth, screenHeight) {
      val rootPane = new BorderPane()

      val name = "Task Name"
      val description = "Example description"
      val state = "In Progress"
      val priority = "High"
      val tags = "Work, Urgent"

      val header = new VBox {
        spacing = 10
        alignment = Pos.Center
        padding = Insets(20)
        children = Seq(
          new Text(name) {
            style = "-fx-font-size: 20; -fx-font-weight: bold;"
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

      val scrollPaneContent = new VBox {
        spacing = 20
        alignment = Pos.Center
        padding = Insets(20)
        children = Seq(
          header,
          detailsTable,
          new Text("Dates") {
            style = "-fx-font-size: 20; -fx-font-weight: bold;"
          },
          datesTable
        )
      }

      val scrollPane = new ScrollPane {
        content = scrollPaneContent
      }

      rootPane.center = scrollPane
      root = rootPane
    }
  }

  private def createTable(data: Seq[(String, String)]): TableView[(String, String)] = {
  val tableData = ObservableBuffer(data: _*)

  val keyColumn = new TableColumn[Tuple2[String, String], String]("Property") {
    cellValueFactory = { cell =>
      scalafx.beans.property.ObjectProperty(cell.value._1)
    }
    prefWidth = data.map(_._1.length).max * 10.0 + 50
  }

  val valueColumn = new TableColumn[Tuple2[String, String], String]("Value") {
    cellValueFactory = { cell =>
      scalafx.beans.property.ObjectProperty(cell.value._2)
    }
    prefWidth = data.map(_._2.length).max * 10.0 + 50
  }

  new TableView[(String, String)](tableData) {
    columns.setAll(keyColumn, valueColumn)
    columnResizePolicy = TableView.ConstrainedResizePolicy
  }
}
}