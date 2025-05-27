package me.timelytask.view.gui

import me.timelytask.view.viewmodel.TaskEditViewModel
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, DatePicker, Label, TextArea, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.text.{Font, FontWeight}

object TaskEditViewGuiFactory {
  /**
   * Aktualisiert den Inhalt des BorderPane mit Daten aus dem ViewModel.
   *
   * @param viewModel Das ViewModel mit den anzuzeigenden Task-Bearbeitungsdaten
   * @param rootPane Das BorderPane, das aktualisiert werden soll
   */
  def updateContent(viewModel: TaskEditViewModel, rootPane: Option[BorderPane]): Unit = {
    // Header mit Titel
    val headerLabel = new Label("Aufgabe bearbeiten") {
      font = Font.font("Arial", FontWeight.Bold, 20)
    }

    val header = new VBox(10) {
      alignment = Pos.Center
      padding = Insets(15)
      children = Seq(headerLabel)
    }

    // Formularfelder für die Aufgabe
    val titleField = new TextField {
      promptText = "Titel der Aufgabe"
      prefWidth = 400
    }

    val descriptionArea = new TextArea {
      promptText = "Beschreibung"
      prefWidth = 400
      prefHeight = 150
    }

    val datePicker = new DatePicker {
      promptText = "Fälligkeitsdatum"
      prefWidth = 200
    }

    // Buttons für Speichern und Abbrechen
    val saveButton = new Button("Speichern") {
      prefWidth = 120
      style = "-fx-base: #90ee90;" // Leicht grünlich
    }

    val cancelButton = new Button("Abbrechen") {
      prefWidth = 120
    }

    val buttonBox = new HBox(20) {
      alignment = Pos.Center
      children = Seq(saveButton, cancelButton)
    }

    // Container für alle Formularelemente
    val formLayout = new VBox(15) {
      alignment = Pos.TopCenter
      padding = Insets(20)
      children = Seq(
        new Label("Titel:"),
        titleField,
        new Label("Beschreibung:"),
        descriptionArea,
        new Label("Fälligkeitsdatum:"),
        datePicker,
        new VBox(30) { children = Seq(buttonBox) }
      )
    }

    // Setze die Komponenten im BorderPane
    rootPane.get.top = header
    rootPane.get.center = formLayout
  }
}