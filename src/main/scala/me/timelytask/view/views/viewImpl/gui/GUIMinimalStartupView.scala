package me.timelytask.view.views.viewImpl.gui

import me.timelytask.view.views.MinimalStartUpView
import scalafx.application.Platform
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{ Button, Label, TextField }
import scalafx.scene.layout.VBox
import scalafx.stage.Stage

class GUIMinimalStartupView extends MinimalStartUpView {
  private var stage: Option[Stage] = None

  override def render(onUserInput: String => Unit): Unit = {
    Platform.runLater {
      val initialScene = getScene(onUserInput)
      stage = Some(new Stage {
        title = "TimelyTask"
        scene = initialScene
      })
      stage.foreach { s =>
        s.setOnCloseRequest(_ => {
          s.close()
        })
        s.show()
      }
    }
  }

  override def kill(): Unit = Platform.runLater {
    stage.foreach(_.close())
  }

  private def getScene(onUserInput: String => Unit): Scene = {
    new Scene(300, 300) {
      val usernameField: TextField = new TextField {
        style = "-fx-font-size: 14px;"
        promptText = "Benutzername eingeben"
        maxWidth = 250
      }

      val errorLabel: Label = new Label {
        text = ""
        style = "-fx-text-fill: red;"
        visible = false
      }

      val enterButton: Button = new Button("Login") {
        disable <== usernameField.text.isEmpty
        onAction = _ => submit()
      }

      usernameField.onAction = _ => submit()

      def submit(): Unit = {
        val name = usernameField.text.value.trim
        if (name.nonEmpty) {
          errorLabel.visible = false
          stage.foreach(_.close())
          onUserInput(name)
        } else {
          errorLabel.text = "Benutzername darf nicht leer sein."
          errorLabel.visible = true
        }
      }

      root = new VBox(10) {
        alignment = Pos.Center
        children = Seq(
          new Label("Willkommen bei TimelyTask!") {
            style = "-fx-font-size: 18px; -fx-font-weight: bold;"
          },
          new Label("Bitte Benutzernamen eingeben:"),
          usernameField,
          enterButton,
          errorLabel
        )
      }
    }
  }
}
