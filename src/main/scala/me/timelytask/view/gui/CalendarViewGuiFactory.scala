package me.timelytask.view.gui

import me.timelytask.view.viewmodel.CalendarViewModel
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.text.{Font, FontWeight}
import java.time.LocalDate

object CalendarViewGuiFactory {
  /**
   * Aktualisiert die Scene mit Daten aus dem ViewModel oder erstellt eine neue Scene.
   *
   * @param viewModel Das ViewModel mit den anzuzeigenden Kalenderdaten
   * @param currentScene Die aktuelle Scene, falls vorhanden
   * @return Die aktualisierte oder neu erstellte Scene
   */
  def updateContent(viewModel: CalendarViewModel, currentScene: Option[Scene]): Option[Scene] = {
    // Erstelle ein neues BorderPane für die Kalenderansicht
    val rootPane = new BorderPane()

    // Header mit Titel und Navigationselementen
    val headerLabel = new Label("Kalenderansicht") {
      font = Font.font("Arial", FontWeight.Bold, 20)
    }

    val navButtons = new HBox(10) {
      alignment = Pos.Center
      children = Seq(
        new Button("◀ Vorheriger Monat"),
        new Button("Heute"),
        new Button("Nächster Monat ▶")
      )
    }

    val header = new VBox(10) {
      alignment = Pos.Center
      padding = Insets(15)
      children = Seq(headerLabel, navButtons)
    }

    // Einfache Platzhalteranzeige für den Kalenderinhalt
    val currentDate = LocalDate.now()
    val contentLabel = new Label(s"Kalenderansicht (Heute: ${currentDate})") {
      font = Font.font("Arial", 16)
    }

    val infoLabel = new Label("Kalender-ViewModel wurde geladen") {
      font = Font.font("Arial", 14)
    }

    val content = new VBox(20) {
      alignment = Pos.Center
      padding = Insets(20)
      children = Seq(contentLabel, infoLabel)

      // Füge einen Dummy-Button hinzu
      children += new Button("Neue Aufgabe erstellen") {
        prefWidth = 200
      }
    }

    // Setze die Komponenten im BorderPane
    rootPane.top = header
    rootPane.center = content

    val scene = currentScene match {
      case Some(existingScene) =>
        existingScene.root = rootPane
        existingScene
      case None => new Scene(rootPane, 800, 600)
    }

    Some(scene)
  }
}