package me.timelytask.view.views.viewImpl.gui

import com.github.nscala_time.time.Imports.{ DateTime, Interval, Period }
import me.timelytask.model.task.Task
import me.timelytask.model.utility.TimeSelection
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.commonsModules.CalendarCommonsModule
import org.joda.time.DateTime
import scalafx.application.Platform
import scalafx.geometry.{ HPos, Insets, Pos, VPos }
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.*
import scalafx.scene.layout.*
import scalafx.scene.paint.Color
import scalafx.scene.text.{ Font, FontWeight }
import scalafx.stage.{ DirectoryChooser, FileChooser }

import java.io.File
import java.time.LocalDate
import java.time.format.{ DateTimeFormatter, TextStyle }
import java.util.Locale

object CalendarViewGuiFactory {

  private val startTimeHour = 8
  private val endTimeHour = 19
  private val numberOfTimeSlots = endTimeHour - startTimeHour

  private val germanLocale = new Locale("de", "DE")

  private val dayMonthFormatter =
    DateTimeFormatter.ofPattern("dd.MM", germanLocale)
  private val dayFormatter = DateTimeFormatter.ofPattern("dd.", germanLocale)

  private val dayMonthYearFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yyyy", germanLocale)

  private def toJavaLocalDate(dateTime: DateTime): LocalDate = {
    LocalDate.of(
      dateTime.getYear,
      dateTime.getMonthOfYear,
      dateTime.getDayOfMonth
    )
  }

  /**
   * Updates or creates the content pane for the calendar view.
   *
   * @param viewModel The CalendarViewModel containing data for the view, including tasks.
   * @param currentScene The current scene, if any.
   * @param viewTypeCommonsModule Common module for calendar view interactions.
   * @return A Pane representing the calendar view.
   */
  def updateContent(
      viewModel: CalendarViewModel,
      currentScene: Option[Scene],
      viewTypeCommonsModule: CalendarCommonsModule
    ): Pane = {
    val rootPane = new BorderPane()

    rootPane.setPrefSize(1000, 800)

    val timeSelection: TimeSelection = viewModel.timeSelection
    val daySpanList: List[DateTime] = timeSelection.getDaySpan

    val (_, _, dateSpanStringToDisplay) = {
      if (daySpanList.nonEmpty) {
        val firstD = toJavaLocalDate(daySpanList.head)
        val lastD = toJavaLocalDate(daySpanList.last)

        val startDayText = firstD.format(dayFormatter)
        val endDayMonthYearText = lastD.format(dayMonthYearFormatter)
        (firstD, lastD, s"${startDayText} - ${endDayMonthYearText}")
      } else {
        System.err.println(
          "CalendarViewGuiFactory: timeSelection.getDaySpan is empty. Displaying" +
            " empty date range."
        )
        (LocalDate.now(), LocalDate.now(), "Keine Daten - Keine Daten")
      }
    }

    val headerLabel = new Label("Wochenkalender") {
      font = Font.font("Arial", FontWeight.Bold, 20)
    }

    val dateSpanLabel = new Label(dateSpanStringToDisplay) {
      font = Font.font("Arial", FontWeight.Normal, 14)
      textFill = Color.Grey
    }

    val todayBtn = new Button("Heute") {
      onAction = _ => viewTypeCommonsModule.eventContainer.goToToday()
    }
    val prevWeekBtn = new Button("<< Woche") {
      onAction = _ => viewTypeCommonsModule.eventContainer.previousWeek()
    }
    val prevDayBtn = new Button("< Tag") {
      onAction = _ => viewTypeCommonsModule.eventContainer.previousDay()
    }
    val nextDayBtn = new Button("> Tag") {
      onAction = _ => viewTypeCommonsModule.eventContainer.nextDay()
    }
    val nextWeekBtn = new Button(">> Woche") {
      onAction = _ => viewTypeCommonsModule.eventContainer.nextWeek()
    }

    val newTaskBtn = new Button("Neue Random Task") {
      onAction = _ => viewTypeCommonsModule.globalEventContainer.addRandomTask()
    }

    val newWindowBtn = new Button("Neues Fenster") {
      onAction = _ => viewTypeCommonsModule.globalEventContainer.newWindow()
    }

    val newInstanceBtn = new Button("Neue Instanz") {
      onAction = _ => viewTypeCommonsModule.globalEventContainer.newInstance()
    }

    val undoBtn = new Button("Undo") {
      onAction = _ => viewTypeCommonsModule.globalEventContainer.undo()
    }

    val redoBtn = new Button("Redo") {
      onAction = _ => viewTypeCommonsModule.globalEventContainer.redo()
    }

    val shutdownApplicationBtn = new Button("App beenden") {
      onAction = _ =>
        viewTypeCommonsModule.globalEventContainer.shutdownApplication()
    }

    val globalNavBar = new HBox() {
      alignment = Pos.Center
      spacing = 5
      children = Seq(
        undoBtn,
        redoBtn,
        newWindowBtn,
        newInstanceBtn,
        shutdownApplicationBtn
      )
    }

    val navBar = new HBox() {
      alignment = Pos.Center
      children = new ButtonBar {
        buttons = Seq(
          todayBtn,
          prevWeekBtn,
          prevDayBtn,
          nextDayBtn,
          nextWeekBtn,
          newTaskBtn
        )
      }
    }

    val globalMenuBar = new MenuBar {
      minHeight = 30
      maxHeight = 30
      menus = Seq(
        new Menu("Datei") {
          items = Seq(
            new Menu("Modell exportieren") {
              items = Seq(
                new MenuItem("JSON") {
                  onAction = _ =>
                    getFolderLocation(folderPath =>
                      viewTypeCommonsModule.globalEventContainer
                        .exportModel("json", folderPath)
                    )
                },
                new MenuItem("XML") {
                  onAction = _ =>
                    getFolderLocation(folderPath =>
                      viewTypeCommonsModule.globalEventContainer
                        .exportModel("xml", folderPath)
                    )
                },
                new MenuItem("YAML") {
                  onAction = _ =>
                    getFolderLocation(folderPath =>
                      viewTypeCommonsModule.globalEventContainer
                        .exportModel("yaml", folderPath)
                    )
                }
              )
            },
            new Menu("Modell importieren") {
                items = Seq(
                    new MenuItem("JSON") {
                    onAction = _ =>
                        getFileName("json", fileName =>
                        viewTypeCommonsModule.globalEventContainer
                            .importModel("json", Some(fileName))
                        )
                    },
                    new MenuItem("XML") {
                    onAction = _ =>
                        getFileName("xml", fileName =>
                        viewTypeCommonsModule.globalEventContainer
                            .importModel("xml", Some(fileName))
                        )
                    },
                    new MenuItem("YAML") {
                    onAction = _ =>
                        getFileName("yaml", fileName =>
                        viewTypeCommonsModule.globalEventContainer
                            .importModel("yaml", Some(fileName))
                        )
                    }
                )
            },
          )
        },
        new Menu("Bearbeiten") {
          items = Seq(
            new MenuItem("Undo") {
              onAction = _ => viewTypeCommonsModule.globalEventContainer.undo()
            },
            new MenuItem("Redo") {
              onAction = _ => viewTypeCommonsModule.globalEventContainer.redo()
            }
          )
        },
        new Menu("Ansicht") {
          items = Seq(
            new MenuItem("Heute") {
              onAction = _ => viewTypeCommonsModule.eventContainer.goToToday()
            },
            new MenuItem("Vorherige Woche") {
              onAction =
                _ => viewTypeCommonsModule.eventContainer.previousWeek()
            },
            new MenuItem("Nächste Woche") {
              onAction = _ => viewTypeCommonsModule.eventContainer.nextWeek()
            },
            new MenuItem("Vorheriger Tag") {
              onAction = _ => viewTypeCommonsModule.eventContainer.previousDay()
            },
            new MenuItem("Nächster Tag") {
              onAction = _ => viewTypeCommonsModule.eventContainer.nextDay()
            }
          )
        },
        new Menu("Fenster") {
          items = Seq(
            new MenuItem("Neues Fenster") {
              onAction =
                _ => viewTypeCommonsModule.globalEventContainer.newWindow()
            },
            new MenuItem("Neue Instanz") {
              onAction =
                _ => viewTypeCommonsModule.globalEventContainer.newInstance()
            },
            new SeparatorMenuItem(),
            new MenuItem("Beenden") {
              onAction = _ =>
                viewTypeCommonsModule.globalEventContainer.shutdownApplication()
            }
          )
        }
      )
    }

    val header = new VBox(5) {
      alignment = Pos.Center
      padding = Insets(10)
      children =
        Seq(globalMenuBar, headerLabel, dateSpanLabel, globalNavBar, navBar)
    }

    val calendarGrid = createCalendarGrid(viewModel)

    rootPane.top = header
    rootPane.center = calendarGrid
    rootPane
  }

  /**
   * Creates the main grid for the calendar, including day headers, time slots, and task cells.
   *
   * @param viewModel The CalendarViewModel containing time selection and tasks.
   *                  It is assumed that `viewModel` has a property `tasks: List[Task]`.
   * @return A GridPane representing the calendar layout.
   */
  private def createCalendarGrid(viewModel: CalendarViewModel): GridPane = {
    val grid = new GridPane {
      hgap = 2; vgap = 2; padding = Insets(5)
      // Debugging layout: style = "-fx-grid-lines-visible: true"
    }

    val weekDates: Seq[LocalDate] =
      viewModel.timeSelection.getDaySpan.map(toJavaLocalDate)

    val allTasks: List[Task] = viewModel.model.tasks

    grid.columnConstraints.add(new ColumnConstraints {
      halignment = HPos.Right; minWidth = 50; prefWidth = 60
    })

    // Columns for days
    (0 until weekDates.length).foreach { _ =>
      grid.columnConstraints.add(new ColumnConstraints {
        minWidth = 80; prefWidth = 110;
        hgrow = scalafx.scene.layout.Priority.Always
      })
    }

    // Row for day headers
    grid.rowConstraints.add(new RowConstraints {
      minHeight = 30; prefHeight = 40; valignment = VPos.Center
    })
    // Rows for time slots
    (0 until numberOfTimeSlots).foreach { _ =>
      grid.rowConstraints.add(new RowConstraints {
        minHeight = 40; prefHeight = 50;
        vgrow = scalafx.scene.layout.Priority.Always
      })
    }

    if (weekDates.isEmpty) {
      System.err.println("CalendarViewGuiFactory: No dates from timeSelection to display in grid.")
    }

    // Day header labels (e.g., Mo 01.07)
    weekDates.zipWithIndex.foreach {
      case (date, colIdx) =>
        val dayOfWeekShortName =
          date.getDayOfWeek.getDisplayName(TextStyle.SHORT, germanLocale)
        val dayDateText =
          s"$dayOfWeekShortName ${date.format(dayMonthFormatter)}"

        val dayLabel = new Label(dayDateText) {
          font = Font.font(null, FontWeight.Bold, 14);
          alignmentInParent = Pos.Center; maxWidth = Double.MaxValue
        }
        GridPane.setColumnIndex(dayLabel, colIdx + 1);
        GridPane.setRowIndex(dayLabel, 0)
        grid.children.add(dayLabel)
    }

    // Time labels (e.g., 08:00)
    (0 until numberOfTimeSlots).foreach { hourOffset =>
      val hour = startTimeHour + hourOffset
      val timeLabel = new Label(f"$hour%02d:00") {
        padding = Insets(0, 5, 0, 0); alignmentInParent = Pos.CenterRight;
        font = Font.font(null, FontWeight.Normal, 12)
      }
      GridPane.setColumnIndex(timeLabel, 0);
      GridPane.setRowIndex(timeLabel, hourOffset + 1)
      grid.children.add(timeLabel)
    }

    // Cells
    for {
      (date, dayCol) <- weekDates.zipWithIndex
      timeRow <- 0 until numberOfTimeSlots
    } {
      // `hour` is the starting hour for the current cell's time slot (e.g., 8 for 8:00-9:00)
      val hour = startTimeHour + timeRow

      val tasksInCell: List[Task] = allTasks.filter { task =>
        val taskStartJodaDateTime: DateTime = task.scheduleDate

        val taskStartDateJava: LocalDate =
          toJavaLocalDate(taskStartJodaDateTime)
        val taskStartActualHour: Int = taskStartJodaDateTime.getHourOfDay

        taskStartDateJava.equals(date) && taskStartActualHour == hour
      }
      val cellContent = tasksInCell.map(_.name).mkString("\n")
      val cell = new TextArea {
        text = cellContent
        wrapText = true
        editable = false
        if (tasksInCell.nonEmpty) {
          style = "-fx-control-inner-background: #e0f7fa;"
        }
      }
      GridPane.setColumnIndex(cell, dayCol + 1)
      GridPane.setRowIndex(cell, timeRow + 1)
      grid.children.add(cell)
    }
    grid
  }

  private def getFolderLocation(callback: Option[String] => Unit): Unit = {
    Platform.runLater {
      val fileChooser = new DirectoryChooser {
        title = "Ordner auswählen"
        initialDirectory = new File(System.getProperty("user.home"))
      }
      val selectedFolder: Option[File] = Option(fileChooser.showDialog(null))
      selectedFolder match {
        case Some(folder) =>
          val folderPath = Some(folder.getAbsolutePath)
          callback(folderPath)
        case None =>
          callback(None) // No folder selected
      }
    }
  }

  private def getFileName(serializationType: String, callback: String => Unit): Unit = {
    Platform.runLater {
      val fileChooser = new FileChooser {
        title = "Datei auswählen"
        initialDirectory = new File(System.getProperty("user.home"))
        extensionFilters.add(
          serializationType match {
            case "json" => new FileChooser.ExtensionFilter("JSON-Datei", "*.json")
            case "xml"  => new FileChooser.ExtensionFilter("XML-Datei", "*.xml")
            case "yaml" => new FileChooser.ExtensionFilter("YAML-Datei", "*.yaml")
          }
        )
      }
      val selectedFile: Option[File] = Option(fileChooser.showOpenDialog(null))
      selectedFile match {
        case Some(file) =>
          val fileName = file.getAbsolutePath
          callback(fileName)
        case None =>
      }
    }
  }
}

//Platform.runLater {
//  val fileChooser = new FileChooser {
//    title = "Modell exportieren unter..."
//    initialFileName = s"${DateTime.now().toString("yyyy_MM_dd")}_TimelyTask.json"
//    extensionFilters.addAll(
//      new FileChooser.ExtensionFilter("JSON-Datei", "*.json"),
//      new FileChooser.ExtensionFilter("XML-Datei", "*.xml"),
//      new FileChooser.ExtensionFilter("Alle Dateien", "*.*")
//    )
//  }
//
//  val selectedFile: Option[File] = Option(fileChooser.showSaveDialog(mainStage))
//
//  selectedFile.foreach { file =>
//    val folderPath = Option(file.getParent)
//    val fileName = Some(file.getName)
//    val serializationType = file.getName.split('.').lastOption.getOrElse("json").toLowerCase
//
//    val success = coreModule.controllers.persistenceController.saveModel(userToken, folderPath,
//    fileName, serializationType)
//
//    if (success) {
//      new Alert(AlertType.Information) {
//        initOwner(mainStage)
//        title = "Export erfolgreich"
//        headerText = "Das Modell wurde erfolgreich exportiert."
//        contentText = s"Gespeichert unter: ${file.getAbsolutePath}"
//      }.showAndWait()
//    } else {
//      new Alert(AlertType.Error) {
//        initOwner(mainStage)
//        title = "Export fehlgeschlagen"
//        headerText = "Das Modell konnte nicht exportiert werden."
//        contentText = "Bitte überprüfen Sie die Konsolenausgabe für weitere Details."
//      }.showAndWait()
//    }
//  }
//}
