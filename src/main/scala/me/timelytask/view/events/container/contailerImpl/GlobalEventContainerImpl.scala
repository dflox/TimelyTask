package me.timelytask.view.events.container.contailerImpl

import me.timelytask.core.CoreModule
import me.timelytask.model.settings.UIType.GUI
import me.timelytask.model.settings.{TABLE, TASKEdit, ViewType}
import me.timelytask.model.task.Task
import me.timelytask.util.Publisher
import me.timelytask.view.UiInstance
import me.timelytask.view.events.EventHandler
import me.timelytask.view.events.container.GlobalEventContainer
import me.timelytask.view.events.event.Event
import org.joda.time.DateTime
import scalafx.application.Platform
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.stage.FileChooser

import java.io.File
import scala.util.Random

// TODO: add GlobalEventResolver and extend the Keymap to include the global events
class GlobalEventContainerImpl(coreModule: CoreModule,
                               eventHandler: EventHandler,
                               activeViewPublisher: Publisher[ViewType],
                               uiInstance: UiInstance,
                               override val userToken: String
                              ) extends GlobalEventContainer {

  override def undo(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      coreModule.controllers.commandHandler.undo()
      true
    },
    (args: Unit) => None,
    ()
  ) {})

  override def redo(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      coreModule.controllers.commandHandler.redo()
      true
    },
    (args: Unit) => None,
    ()
  ) {})

  override def switchToView(): Unit = {
    val event = new Event[Unit](
      _ => {
        if(activeViewPublisher.getValue.contains(TASKEdit)) false
        else {
          activeViewPublisher.update(Some(TABLE))
          true
        }
      },
      _ => None,
      ()
    ){}
    eventHandler.handle(event)
  }

  override def shutdownApplication(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      coreModule.controllers.coreController.shutdownApplication()
      true
    },
    (args: Unit) => None,
    ()
  ){})

  override def newWindow(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      uiInstance.addUi(GUI)
      true
    },
    (args: Unit) => None,
    ()
  ){})

  override def newInstance(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      coreModule.controllers.coreController.newGuiInstance()
      true
    },
    (args: Unit) => None,
    ()
  ){})

  override def addRandomTask(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      coreModule.controllers.modelController.addTask(userToken, randomTask())
      true
    },
    (args: Unit) => None,
    ()
  ){})

  override def exportModel(): Unit = {
    val stageOption = uiInstance.uiManager.flatMap(_.stage).headOption

    stageOption.foreach { mainStage =>
      Platform.runLater {
        val fileChooser = new FileChooser {
          title = "Modell exportieren unter..."
          initialFileName = s"${DateTime.now().toString("yyyy_MM_dd")}_TimelyTask.json"
          extensionFilters.addAll(
            new FileChooser.ExtensionFilter("JSON-Datei", "*.json"),
            new FileChooser.ExtensionFilter("XML-Datei", "*.xml"),
            new FileChooser.ExtensionFilter("Alle Dateien", "*.*")
          )
        }

        val selectedFile: Option[File] = Option(fileChooser.showSaveDialog(mainStage))

        selectedFile.foreach { file =>
          val folderPath = Option(file.getParent)
          val fileName = Some(file.getName)
          val serializationType = file.getName.split('.').lastOption.getOrElse("json").toLowerCase

          val success = coreModule.controllers.persistenceController.saveModel(userToken, folderPath, fileName, serializationType)

          if (success) {
            new Alert(AlertType.Information) {
              initOwner(mainStage)
              title = "Export erfolgreich"
              headerText = "Das Modell wurde erfolgreich exportiert."
              contentText = s"Gespeichert unter: ${file.getAbsolutePath}"
            }.showAndWait()
          } else {
            new Alert(AlertType.Error) {
              initOwner(mainStage)
              title = "Export fehlgeschlagen"
              headerText = "Das Modell konnte nicht exportiert werden."
              contentText = "Bitte überprüfen Sie die Konsolenausgabe für weitere Details."
            }.showAndWait()
          }
        }
      }
    }
  }
  
  private def randomTask(): Task = {
    new Task(
      "Random Task",
      "Random Task Description",
      scheduleDate = DateTime.now()
        .withTime(8, 0, 0, 0)
        .plusMinutes(Random.nextInt(60*10))// random time today
    )
  }

  override def newTask(): Unit = ()  //TODO
  
  // TODO: Update Task, change settings, change theme, change view (?) etc.

  override def closeInstance(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      coreModule.controllers.coreController.closeInstance(uiInstance)
      true
    },
    (args: Unit) => None,
    ()
  ){})

  
}
