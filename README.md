![Static Badge](https://img.shields.io/badge/Scala-sbt-red?style=for-the-badge&logo=Scala&logoColor=%23dc322f&color=%23dc322f)
[![Coverage Status](https://coveralls.io/repos/github/dflox/TimelyTask/badge.svg)](https://coveralls.io/github/dflox/TimelyTask)
![Tests](https://github.com/dflox/TimelyTask/actions/workflows/ci.yml/badge.svg)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/dflox/TimelyTask)


# TimelyTask

TimelyTask is a Scala-based application designed to efficiently manage your calendar and tasks. It's built to be as agile as possible, offering flexible options for organizing and planning your activities. In the future, TimelyTask will intelligently manage your tasks to optimize your schedule and help you become more productive.

This is an educational project for the Software Engineering module at HTWG Konstanz written in Scala.

---

## Table of Contents

- [Keyboard Shortcuts and Functions](#Keyboard-Shortcuts-and-Functions)
- [Instalation](#Instalation)
- [License](#License)
- [Acknowledgements](#Acknowledgements)
- [Contact](#Contact)

---
## Keyboard Shortcuts and Functions

Use the following key combinations in the Terminal UI:

### Calendar Navigation
| Shortcut          | Function                 |
|-------------------|--------------------------|
| **Shift + →**     | Move to next day         |
| **Shift + ←**     | Move to previous day     |
| **Ctrl + →**      | Jump to next week        |
| **Ctrl + ←**      | Jump to previous week    |
| **T**             | Go to today              |

### General Actions
| Shortcut          | Function                        |
|-------------------|---------------------------------|
| **Z**             | Undo                            |
| **Y**             | Redo                            |
| **R**             | Create random test task         |
| **Ctrl + G**      | Open new GUI window             |
| **Ctrl + I**      | Start new instance              |
| **Ctrl + X**      | Close current instance          |
| **F4** | Close Application |

### Import / Export
| Shortcut          | Function                   |
|-------------------|----------------------------|
| **1**             | Export to JSON             |
| **2**             | Export to YAML             |
| **3**             | Export to XML              |
| **Shift + 1**     | Import from JSON           |
| **Shift + 2**     | Import from YAML           |
| **Shift + 3**     | Import from XML            |

---

## Installation
### Prerequisites

- [Scala](https://www.scala-lang.org/download/)
- [sbt (Scala Build Tool)](https://www.scala-sbt.org/download.html)
- [jdk 24](https://jdk.java.net/24/)

### Clone the Repository

```sh
git clone https://github.com/dflox/TimelyTask.git
cd TimelyTask
````
## Build and run the Application

```sh
sbt compile
sbt run
````
---

## License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/UnKompetent/TimelyTask/blob/main/LICENSE) file for details.

---

## Acknowledgements

- [nscala-time](https://github.com/nscala-time/nscala-time): A Scala wrapper for Joda Time.
- [ScalaTest](https://www.scalatest.org/): A testing tool for Scala and Java.

---

## Contact

For any questions or feedback, please open an issue on the [GitHub repository](https://github.com/dflox/TimelyTask/issues).

Codegrid
Statement List
1 package me.timelytask.view.views.viewImpl.gui
2
3 import com.github.nscala_time.time.Imports.{ DateTime, Interval, Period }
4 import me.timelytask.model.task.Task
5 import me.timelytask.model.utility.TimeSelection
6 import me.timelytask.view.viewmodel.CalendarViewModel
7 import me.timelytask.view.views.commonsModules.CalendarCommonsModule
8 import org.joda.time.DateTime
9 import scalafx.application.Platform
10 import scalafx.geometry.{ HPos, Insets, Pos, VPos }
11 import scalafx.scene.Scene
12 import scalafx.scene.control.Alert.AlertType
13 import scalafx.scene.control.*
14 import scalafx.scene.layout.*
15 import scalafx.scene.paint.Color
16 import scalafx.scene.text.{ Font, FontWeight }
17 import scalafx.stage.{ DirectoryChooser, FileChooser }
18
19 import java.io.File
20 import java.time.LocalDate
21 import java.time.format.{ DateTimeFormatter, TextStyle }
22 import java.util.Locale
23
24 object CalendarViewGuiFactory {
25
26   private val startTimeHour = 8
27   private val endTimeHour = 19
28   private val numberOfTimeSlots = endTimeHour - startTimeHour
29
30   private val germanLocale = new Locale("de", "DE")
31
32   private val dayMonthFormatter =
33     DateTimeFormatter.ofPattern("dd.MM", germanLocale)
34   private val dayFormatter = DateTimeFormatter.ofPattern("dd.", germanLocale)
35
36   private val dayMonthYearFormatter =
37     DateTimeFormatter.ofPattern("dd.MM.yyyy", germanLocale)
38
39   private def toJavaLocalDate(dateTime: DateTime): LocalDate = {
40     LocalDate.of(
41       dateTime.getYear,
42       dateTime.getMonthOfYear,
43       dateTime.getDayOfMonth
44     )
45   }
46
47   /**
48    * Updates or creates the content pane for the calendar view.
49    *
50    * @param viewModel The CalendarViewModel containing data for the view, including tasks.
51    * @param currentScene The current scene, if any.
52    * @param viewTypeCommonsModule Common module for calendar view interactions.
53    * @return A Pane representing the calendar view.
54    */
55   def updateContent(
56       viewModel: CalendarViewModel,
57       currentScene: Option[Scene],
58       viewTypeCommonsModule: CalendarCommonsModule
59     ): Pane = {
60     val rootPane = new BorderPane()
61
62     rootPane.setPrefSize(1000, 800)
63
64     val timeSelection: TimeSelection = viewModel.timeSelection
65     val daySpanList: List[DateTime] = timeSelection.getDaySpan
66
67     val (_, _, dateSpanStringToDisplay) = {
68       if (daySpanList.nonEmpty) {
69         val firstD = toJavaLocalDate(daySpanList.head)
70         val lastD = toJavaLocalDate(daySpanList.last)
71
72         val startDayText = firstD.format(dayFormatter)
73         val endDayMonthYearText = lastD.format(dayMonthYearFormatter)
74         (firstD, lastD, s"${startDayText} - ${endDayMonthYearText}")
75       } else {
76         System.err.println(
77           "CalendarViewGuiFactory: timeSelection.getDaySpan is empty. Displaying" +
78             " empty date range."
79         )
80         (LocalDate.now(), LocalDate.now(), "Keine Daten - Keine Daten")
81       }
82     }
83
84     val headerLabel = new Label("Wochenkalender") {
85       font = Font.font("Arial", FontWeight.Bold, 20)
86     }
87
88     val dateSpanLabel = new Label(dateSpanStringToDisplay) {
89       font = Font.font("Arial", FontWeight.Normal, 14)
90       textFill = Color.Grey
91     }
92
93     val todayBtn = new Button("Heute") {
94       onAction = _ => viewTypeCommonsModule.eventContainer.goToToday()
95     }
96     val prevWeekBtn = new Button("<< Woche") {
97       onAction = _ => viewTypeCommonsModule.eventContainer.previousWeek()
98     }
99     val prevDayBtn = new Button("< Tag") {
100       onAction = _ => viewTypeCommonsModule.eventContainer.previousDay()
101     }
102     val nextDayBtn = new Button("> Tag") {
103       onAction = _ => viewTypeCommonsModule.eventContainer.nextDay()
104     }
105     val nextWeekBtn = new Button(">> Woche") {
106       onAction = _ => viewTypeCommonsModule.eventContainer.nextWeek()
107     }
108
109     val newTaskBtn = new Button("Neue Random Task") {
110       onAction = _ => viewTypeCommonsModule.globalEventContainer.addRandomTask()
111     }
112
113     val newWindowBtn = new Button("Neues Fenster") {
114       onAction = _ => viewTypeCommonsModule.globalEventContainer.newWindow()
115     }
116
117     val newInstanceBtn = new Button("Neue Instanz") {
118       onAction = _ => viewTypeCommonsModule.globalEventContainer.newInstance()
119     }
120
121     val undoBtn = new Button("Undo") {
122       onAction = _ => viewTypeCommonsModule.globalEventContainer.undo()
123     }
124
125     val redoBtn = new Button("Redo") {
126       onAction = _ => viewTypeCommonsModule.globalEventContainer.redo()
127     }
128
129     val shutdownApplicationBtn = new Button("App beenden") {
130       onAction = _ =>
131         viewTypeCommonsModule.globalEventContainer.shutdownApplication()
132     }
133
134     val globalNavBar = new HBox() {
135       alignment = Pos.Center
136       spacing = 5
137       children = Seq(
138         undoBtn,
139         redoBtn,
140         newWindowBtn,
141         newInstanceBtn,
142         shutdownApplicationBtn
143       )
144     }
145
146     val navBar = new HBox() {
147       alignment = Pos.Center
148       children = new ButtonBar {
149         buttons = Seq(
150           todayBtn,
151           prevWeekBtn,
152           prevDayBtn,
153           nextDayBtn,
154           nextWeekBtn,
155           newTaskBtn
156         )
157       }
158     }
159
160     val globalMenuBar = new MenuBar {
161       minHeight = 30
162       maxHeight = 30
163       menus = Seq(
164         new Menu("Datei") {
165           items = Seq(
166             new Menu("Modell exportieren") {
167               items = Seq(
168                 new MenuItem("JSON") {
169                   onAction = _ =>
170                     getFolderLocation(folderPath =>
171                       viewTypeCommonsModule.globalEventContainer
172                         .exportModel("json", folderPath)
173                     )
174                 },
175                 new MenuItem("XML") {
176                   onAction = _ =>
177                     getFolderLocation(folderPath =>
178                       viewTypeCommonsModule.globalEventContainer
179                         .exportModel("xml", folderPath)
180                     )
181                 },
182                 new MenuItem("YAML") {
183                   onAction = _ =>
184                     getFolderLocation(folderPath =>
185                       viewTypeCommonsModule.globalEventContainer
186                         .exportModel("yaml", folderPath)
187                     )
188                 }
189               )
190             },
191             new Menu("Modell importieren") {
192                 items = Seq(
193                     new MenuItem("JSON") {
194                     onAction = _ =>
195                         getFileName("json", fileName =>
196                         viewTypeCommonsModule.globalEventContainer
197                             .importModel("json", Some(fileName))
198                         )
199                     },
200                     new MenuItem("XML") {
201                     onAction = _ =>
202                         getFileName("xml", fileName =>
203                         viewTypeCommonsModule.globalEventContainer
204                             .importModel("xml", Some(fileName))
205                         )
206                     },
207                     new MenuItem("YAML") {
208                     onAction = _ =>
209                         getFileName("yaml", fileName =>
210                         viewTypeCommonsModule.globalEventContainer
211                             .importModel("yaml", Some(fileName))
212                         )
213                     }
214                 )
215             },
216           )
217         },
218         new Menu("Bearbeiten") {
219           items = Seq(
220             new MenuItem("Undo") {
221               onAction = _ => viewTypeCommonsModule.globalEventContainer.undo()
222             },
223             new MenuItem("Redo") {
224               onAction = _ => viewTypeCommonsModule.globalEventContainer.redo()
225             }
226           )
227         },
228         new Menu("Ansicht") {
229           items = Seq(
230             new MenuItem("Heute") {
231               onAction = _ => viewTypeCommonsModule.eventContainer.goToToday()
232             },
233             new MenuItem("Vorherige Woche") {
234               onAction =
235                 _ => viewTypeCommonsModule.eventContainer.previousWeek()
236             },
237             new MenuItem("Nächste Woche") {
238               onAction = _ => viewTypeCommonsModule.eventContainer.nextWeek()
239             },
240             new MenuItem("Vorheriger Tag") {
241               onAction = _ => viewTypeCommonsModule.eventContainer.previousDay()
242             },
243             new MenuItem("Nächster Tag") {
244               onAction = _ => viewTypeCommonsModule.eventContainer.nextDay()
245             }
246           )
247         },
248         new Menu("Fenster") {
249           items = Seq(
250             new MenuItem("Neues Fenster") {
251               onAction =
252                 _ => viewTypeCommonsModule.globalEventContainer.newWindow()
253             },
254             new MenuItem("Neue Instanz") {
255               onAction =
256                 _ => viewTypeCommonsModule.globalEventContainer.newInstance()
257             },
258             new SeparatorMenuItem(),
259             new MenuItem("Beenden") {
260               onAction = _ =>
261                 viewTypeCommonsModule.globalEventContainer.shutdownApplication()
262             }
263           )
264         }
265       )
266     }
267
268     val header = new VBox(5) {
269       alignment = Pos.Center
270       padding = Insets(10)
271       children =
272         Seq(globalMenuBar, headerLabel, dateSpanLabel, globalNavBar, navBar)
273     }
274
275     val calendarGrid = createCalendarGrid(viewModel)
276
277     rootPane.top = header
278     rootPane.center = calendarGrid
279     rootPane
280   }
281
282   /**
283    * Creates the main grid for the calendar, including day headers, time slots, and task cells.
284    *
285    * @param viewModel The CalendarViewModel containing time selection and tasks.
286    *                  It is assumed that `viewModel` has a property `tasks: List[Task]`.
287    * @return A GridPane representing the calendar layout.
288    */
289   private def createCalendarGrid(viewModel: CalendarViewModel): GridPane = {
290     val grid = new GridPane {
291       hgap = 2; vgap = 2; padding = Insets(5)
292       // Debugging layout: style = "-fx-grid-lines-visible: true"
293     }
294
295     val weekDates: Seq[LocalDate] =
296       viewModel.timeSelection.getDaySpan.map(toJavaLocalDate)
297
298     val allTasks: List[Task] = viewModel.model.tasks
299
300     grid.columnConstraints.add(new ColumnConstraints {
301       halignment = HPos.Right; minWidth = 50; prefWidth = 60
302     })
303
304     // Columns for days
305     (0 until weekDates.length).foreach { _ =>
306       grid.columnConstraints.add(new ColumnConstraints {
307         minWidth = 80; prefWidth = 110;
308         hgrow = scalafx.scene.layout.Priority.Always
309       })
310     }
311
312     // Row for day headers
313     grid.rowConstraints.add(new RowConstraints {
314       minHeight = 30; prefHeight = 40; valignment = VPos.Center
315     })
316     // Rows for time slots
317     (0 until numberOfTimeSlots).foreach { _ =>
318       grid.rowConstraints.add(new RowConstraints {
319         minHeight = 40; prefHeight = 50;
320         vgrow = scalafx.scene.layout.Priority.Always
321       })
322     }
323
324     if (weekDates.isEmpty) {
325       System.err.println("CalendarViewGuiFactory: No dates from timeSelection to display in grid.")
326     }
327
328     // Day header labels (e.g., Mo 01.07)
329     weekDates.zipWithIndex.foreach {
330       case (date, colIdx) =>
331         val dayOfWeekShortName =
332           date.getDayOfWeek.getDisplayName(TextStyle.SHORT, germanLocale)
333         val dayDateText =
334           s"$dayOfWeekShortName ${date.format(dayMonthFormatter)}"
335
336         val dayLabel = new Label(dayDateText) {
337           font = Font.font(null, FontWeight.Bold, 14);
338           alignmentInParent = Pos.Center; maxWidth = Double.MaxValue
339         }
340         GridPane.setColumnIndex(dayLabel, colIdx + 1);
341         GridPane.setRowIndex(dayLabel, 0)
342         grid.children.add(dayLabel)
343     }
344
345     // Time labels (e.g., 08:00)
346     (0 until numberOfTimeSlots).foreach { hourOffset =>
347       val hour = startTimeHour + hourOffset
348       val timeLabel = new Label(f"$hour%02d:00") {
349         padding = Insets(0, 5, 0, 0); alignmentInParent = Pos.CenterRight;
350         font = Font.font(null, FontWeight.Normal, 12)
351       }
352       GridPane.setColumnIndex(timeLabel, 0);
353       GridPane.setRowIndex(timeLabel, hourOffset + 1)
354       grid.children.add(timeLabel)
355     }
356
357     // Cells
358     for {
359       (date, dayCol) <- weekDates.zipWithIndex
360       timeRow <- 0 until numberOfTimeSlots
361     } {
362       // `hour` is the starting hour for the current cell's time slot (e.g., 8 for 8:00-9:00)
363       val hour = startTimeHour + timeRow
364
365       val tasksInCell: List[Task] = allTasks.filter { task =>
366         val taskStartJodaDateTime: DateTime = task.scheduleDate
367
368         val taskStartDateJava: LocalDate =
369           toJavaLocalDate(taskStartJodaDateTime)
370         val taskStartActualHour: Int = taskStartJodaDateTime.getHourOfDay
371
372         taskStartDateJava.equals(date) && taskStartActualHour == hour
373       }
374       val cellContent = tasksInCell.map(_.name).mkString("\n")
375       val cell = new TextArea {
376         text = cellContent
377         wrapText = true
378         editable = false
379         if (tasksInCell.nonEmpty) {
380           style = "-fx-control-inner-background: #e0f7fa;"
381         }
382       }
383       GridPane.setColumnIndex(cell, dayCol + 1)
384       GridPane.setRowIndex(cell, timeRow + 1)
385       grid.children.add(cell)
386     }
387     grid
388   }
389
390   private def getFolderLocation(callback: Option[String] => Unit): Unit = {
391     Platform.runLater {
392       val fileChooser = new DirectoryChooser {
393         title = "Ordner auswählen"
394         initialDirectory = new File(System.getProperty("user.home"))
395       }
396       val selectedFolder: Option[File] = Option(fileChooser.showDialog(null))
397       selectedFolder match {
398         case Some(folder) =>
399           val folderPath = Some(folder.getAbsolutePath)
400           callback(folderPath)
401         case None =>
402           callback(None) // No folder selected
403       }
404     }
405   }
406
407   private def getFileName(serializationType: String, callback: String => Unit): Unit = {
408     Platform.runLater {
409       val fileChooser = new FileChooser {
410         title = "Datei auswählen"
411         initialDirectory = new File(System.getProperty("user.home"))
412         extensionFilters.add(
413           serializationType match {
414             case "json" => new FileChooser.ExtensionFilter("JSON-Datei", "*.json")
415             case "xml"  => new FileChooser.ExtensionFilter("XML-Datei", "*.xml")
416             case "yaml" => new FileChooser.ExtensionFilter("YAML-Datei", "*.yaml")
417           }
418         )
419       }
420       val selectedFile: Option[File] = Option(fileChooser.showOpenDialog(null))
421       selectedFile match {
422         case Some(file) =>
423           val fileName = file.getAbsolutePath
424           callback(fileName)
425         case None =>
426       }
427     }
428   }
429 }
430
431 //Platform.runLater {
432 //  val fileChooser = new FileChooser {
433 //    title = "Modell exportieren unter..."
434 //    initialFileName = s"${DateTime.now().toString("yyyy_MM_dd")}_TimelyTask.json"
435 //    extensionFilters.addAll(
436 //      new FileChooser.ExtensionFilter("JSON-Datei", "*.json"),
437 //      new FileChooser.ExtensionFilter("XML-Datei", "*.xml"),
438 //      new FileChooser.ExtensionFilter("Alle Dateien", "*.*")
439 //    )
440 //  }
441 //
442 //  val selectedFile: Option[File] = Option(fileChooser.showSaveDialog(mainStage))
443 //
444 //  selectedFile.foreach { file =>
445 //    val folderPath = Option(file.getParent)
446 //    val fileName = Some(file.getName)
447 //    val serializationType = file.getName.split('.').lastOption.getOrElse("json").toLowerCase
448 //
449 //    val success = coreModule.controllers.persistenceController.saveModel(userToken, folderPath,
450 //    fileName, serializationType)
451 //
452 //    if (success) {
453 //      new Alert(AlertType.Information) {
454 //        initOwner(mainStage)
455 //        title = "Export erfolgreich"
456 //        headerText = "Das Modell wurde erfolgreich exportiert."
457 //        contentText = s"Gespeichert unter: ${file.getAbsolutePath}"
458 //      }.showAndWait()
459 //    } else {
460 //      new Alert(AlertType.Error) {
461 //        initOwner(mainStage)
462 //        title = "Export fehlgeschlagen"
463 //        headerText = "Das Modell konnte nicht exportiert werden."
464 //        contentText = "Bitte überprüfen Sie die Konsolenausgabe für weitere Details."
465 //      }.showAndWait()
466 //    }
467 //  }
468 //}