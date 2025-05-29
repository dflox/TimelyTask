package me.timelytask.view.gui

import me.timelytask.view.viewmodel.TaskEditViewModel
import scalafx.geometry.{Insets, Pos, HPos}
import scalafx.scene.control.{Button, ButtonBar, ComboBox, DatePicker, Label, TextArea, TextField}
import scalafx.scene.layout.{BorderPane, ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.scene.text.{Font, FontWeight, Text}
import scalafx.collections.ObservableBuffer

object TaskEditViewGuiFactory {
  /**
   * Aktualisiert den Inhalt des BorderPane mit dem Task-Editor UI.
   *
   * @param viewModel Das ViewModel (aktuell nicht verwendet)
   * @param rootPane Das BorderPane, das aktualisiert werden soll
   */
  def updateContent(viewModel: TaskEditViewModel, rootPane: Option[BorderPane]): Unit = {
    val pane = rootPane.getOrElse(new BorderPane())

    // --- Main Title ---
    val taskEditorTitle = new Text("Task Editor") {
      font = Font.font("System", FontWeight.Bold, 19)
    }

    // --- Name Section ---
    val nameLabel = new Text("Name*") {
      font = Font.font("System", FontWeight.Bold, 12)
    }
    val nameField = new TextField {
      promptText = "Aufgabenname eingeben"
    }
    val nameSection = new VBox(5) {
      children = Seq(nameLabel, nameField)
    }

    // --- Priority and Date Section ---
    val priorityLabel = new Text("Priorität")
    val priorityComboBox = new ComboBox[String] {
      items = ObservableBuffer("Hoch", "Mittel", "Niedrig")
      promptText = "Wählen..."
      prefWidth = 150
    }

    val dateLabel = new Text("Datum")
    val datePicker = new DatePicker()

    val priorityDateGrid = new GridPane {
      hgap = 10
      vgap = 5
      add(priorityLabel, 0, 0)
      add(dateLabel, 1, 0)
      add(priorityComboBox, 0, 1)
      add(datePicker, 1, 1)
    }

    // --- Description Section ---
    val descriptionLabel = new Text("Beschreibung")
    val descriptionArea = new TextArea {
      promptText = "Aufgabenbeschreibung eingeben..."
      prefRowCount = 5
      wrapText = true
    }
    val descriptionSection = new VBox(5) {
      children = Seq(descriptionLabel, descriptionArea)
      VBox.setVgrow(descriptionArea, Priority.Always)
    }

    // --- Button Bar ---
    val cancelButton = new Button("Abbrechen")
    val confirmButton = new Button("Bestätigen") {
      defaultButton = true
      style = "-fx-base: #007bff; -fx-text-fill: white;"
    }
    val buttonBar = new ButtonBar {
      buttons = Seq(cancelButton, confirmButton)
    }

    // --- Main Layout (Root GridPane) ---
    val contentPane = new GridPane {
      hgap = 10
      vgap = 15
      padding = Insets(20)

      columnConstraints = Seq(
        new ColumnConstraints {
          hgrow = Priority.Sometimes
        }
      )

      add(taskEditorTitle, 0, 0)
      add(nameSection, 0, 1)
      add(priorityDateGrid, 0, 2)
      add(descriptionSection, 0, 3)
      add(buttonBar, 0, 4)

      rowConstraints = Seq(
        new RowConstraints { vgrow = Priority.Never },
        new RowConstraints { vgrow = Priority.Never },
        new RowConstraints { vgrow = Priority.Never },
        new RowConstraints { vgrow = Priority.Always },
        new RowConstraints { vgrow = Priority.Never }
      )

      GridPane.setVgrow(descriptionSection, Priority.Always)
      GridPane.setHalignment(buttonBar, HPos.Right)
    }

    pane.center = contentPane
  }
}