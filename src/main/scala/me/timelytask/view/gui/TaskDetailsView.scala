//package me.timelytask.view.gui
//
//import scalafx.scene.Scene
//import scalafx.scene.layout.{BorderPane, VBox, HBox, StackPane}
//import scalafx.scene.text.Text
//import scalafx.geometry.Insets
//import scalafx.geometry.Pos
//import scalafx.scene.paint.Color
//import scalafx.scene.control.{Label, Button, ScrollPane, TableView, TableColumn}
//import scalafx.beans.property.StringProperty
//
//object TaskDetailsView {
//
//  def createTaskDetailsScene(): Scene = {
//    new Scene(800, 600) {
//      val rootPane = new BorderPane()
//
//      // Header
//      val header = new VBox {
//        spacing = 10
//        alignment = Pos.Center
//        padding = Insets(20)
//        children = Seq(
//          new Text("### Name") {
//            style = "-fx-font-size: 20; -fx-font-weight: bold;"
//          },
//          new Text("---") {
//            style = "-fx-font-size: 14;"
//          },
//          new Text("### Details") {
//            style = "-fx-font-size: 20; -fx-font-weight: bold;"
//          }
//        )
//      }
//
//      // Details
//      val detailsTable = createTable(Seq(
//        ("Description", "Example description"),
//        ("State", "In Progress"),
//        ("Priority", "High"),
//        ("Tags", "Work, Urgent")
//      ))
//
//      // Dates
//      val datesHeader = new VBox {
//        spacing = 10
//        alignment = Pos.Center
//        padding = Insets(20)
//        children = Seq(
//          new Text("---") {
//            style = "-fx-font-size: 14;"
//          },
//          new Text("### Dates") {
//            style = "-fx-font-size: 20; -fx-font-weight: bold;"
//          }
//        )
//      }
//
//      val datesTable = createTable(Seq(
//        ("Deadline", "2024-12-20"),
//        ("Schedule Date", "2024-12-15"),
//        ("Recurrence Interval", "Weekly")
//      ))
//
//      // Other
//      val otherHeader = new VBox {
//        spacing = 10
//        alignment = Pos.Center
//        padding = Insets(20)
//        children = Seq(
//          new Text("---") {
//            style = "-fx-font-size: 14;"
//          },
//          new Text("### Other") {
//            style = "-fx-font-size: 20; -fx-font-weight: bold;"
//          }
//        )
//      }
//
//      val otherTable = createTable(Seq(
//        ("...", "..."),
//        ("...", "..."),
//        ("...", "...")
//      ))
//
//      val content = new VBox {
//        spacing = 20
//        alignment = Pos.Center
//        padding = Insets(20)
//        children = Seq(
//          header,
//          detailsTable,
//          datesHeader,
//          datesTable,
//          otherHeader,
//          otherTable
//        )
//      }
//
//      val scrollPane = new ScrollPane {
//        content = content
//        fitToWidth = true
//      }
//
//      rootPane.center = scrollPane
//      root = rootPane
//    }
//  }
//
//  private def createTable(data: Seq[(String, String)]): VBox = {
//    new VBox {
//      spacing = 10
//      alignment = Pos.Center
//      padding = Insets(10)
//      children = data.map { case (key, value) =>
//        new HBox {
//          spacing = 20
//          alignment = Pos.Center
//          children = Seq(
//            new StackPane {
//              alignment = Pos.Center
//              style = "-fx-background-color: lightgray; -fx-padding: 10;"
//              children = new Label(key)
//            },
//            new StackPane {
//              alignment = Pos.Center
//              style = "-fx-background-color: white; -fx-padding: 10;"
//              children = new Label(value)
//            }
//          )
//        }: javafx.scene.Node
//      }
//    }: javafx.scene.layout.VBox
//  }
//
//}
