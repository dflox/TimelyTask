//package me.timelytask.view.views.viewImpl.gui
//
//import com.github.nscala_time.time.Imports.DateTime
//import me.timelytask.model.Model
//import me.timelytask.model.task.Task
//import me.timelytask.model.utility.TimeSelection
//import me.timelytask.view.events.container.{CalendarEventContainer, GlobalEventContainer}
//import me.timelytask.view.viewmodel.CalendarViewModel
//import me.timelytask.view.views.commonsModules.CalendarCommonsModule
//import org.mockito.Mockito.{times, verify, when}
//import org.scalatest.wordspec.AnyWordSpec
//import org.scalatest.matchers.should.Matchers
//import org.scalatest.{BeforeAndAfterAll, OptionValues}
//import org.scalatestplus.mockito.MockitoSugar
//import scalafx.application.Platform
//import scalafx.scene.control.{Button, Label, TextArea}
//import scalafx.scene.layout.{BorderPane, GridPane, HBox, VBox}
//import scalafx.Includes._
//import scalafx.scene.Node
//
//import java.util.UUID
//import java.util.concurrent.CountDownLatch
//
//class CalendarViewGuiFactorySpec extends AnyWordSpec with Matchers with MockitoSugar with BeforeAndAfterAll with OptionValues {
//
//  // --- START: Embedded JavaFX Test Helpers ---
//  override protected def beforeAll(): Unit = {
//    super.beforeAll()
//    try {
//      Platform.startup(() => {})
//    } catch {
//      case _: IllegalStateException => // Platform is already running, which is fine.
//    }
//  }
//
//  def runOnFxThread(block: => Unit): Unit = {
//    val latch = new CountDownLatch(1)
//    Platform.runLater {
//      try {
//        block
//      } finally {
//        latch.countDown()
//      }
//    }
//    latch.await()
//  }
//  // --- END: Embedded JavaFX Test Helpers ---
//
//
//  trait Fixture {
//    val mockViewModel: CalendarViewModel = mock[CalendarViewModel]
//    val mockTimeSelection: TimeSelection = mock[TimeSelection]
//    val mockModel: Model = mock[Model]
//    val mockCommonsModule: CalendarCommonsModule = mock[CalendarCommonsModule]
//    val mockEventContainer: CalendarEventContainer = mock[CalendarEventContainer]
//    val mockGlobalEventContainer: GlobalEventContainer = mock[GlobalEventContainer]
//
//    val testWeekStart: DateTime = new DateTime("2024-07-22T00:00:00")
//    val testWeek: List[DateTime] = (0 to 6).map(testWeekStart.plusDays).toList
//
//    val taskInGrid: Task = Task.exampleTask.copy(
//      uuid = UUID.randomUUID(),
//      name = "Morning Stand-up",
//      scheduleDate = testWeekStart.withHourOfDay(10)
//    )
//
//    val taskNotInGrid: Task = Task.exampleTask.copy(
//      uuid = UUID.randomUUID(),
//      name = "Future Planning",
//      scheduleDate = testWeekStart.plusWeeks(1)
//    )
//
//    when(mockViewModel.timeSelection).thenReturn(mockTimeSelection)
//    when(mockViewModel.model).thenReturn(mockModel)
//    when(mockTimeSelection.getDaySpan).thenReturn(testWeek)
//    when(mockModel.tasks).thenReturn(List(taskInGrid, taskNotInGrid))
//    when(mockCommonsModule.eventContainer).thenReturn(mockEventContainer)
//    when(mockCommonsModule.globalEventContainer).thenReturn(mockGlobalEventContainer)
//  }
//
//  "CalendarViewGuiFactory" should {
//
//    "create the main pane with a correctly formatted date range label" in new Fixture {
//      var rootPane: BorderPane = null
//      runOnFxThread {
//        rootPane = CalendarViewGuiFactory.updateContent(mockViewModel, None, mockCommonsModule).asInstanceOf[BorderPane]
//      }
//
//      rootPane should not be null
//      val header = rootPane.top.value.asInstanceOf[VBox]
//      val dateSpanLabel = header.children.collectFirst {
//        case node if node.delegate.isInstanceOf[javafx.scene.control.Label] &&
//          new Label(node.delegate.asInstanceOf[javafx.scene.control.Label]).text.value.contains(" - ") =>
//          new Label(node.delegate.asInstanceOf[javafx.scene.control.Label])
//      }.value
//      dateSpanLabel.text.value shouldBe "22. - 28.07.2024"
//    }
//
//    "create a calendar grid with correct headers and time slots" in new Fixture {
//      var grid: GridPane = null
//      runOnFxThread {
//        val rootPane = CalendarViewGuiFactory.updateContent(mockViewModel, None, mockCommonsModule).asInstanceOf[BorderPane]
//        grid = rootPane.center.value.asInstanceOf[GridPane]
//      }
//
//      grid.getColumnCount shouldBe (testWeek.length + 1)
//      grid.getRowCount shouldBe ((19 - 8) + 1)
//
//      val dayHeader = getNodeByRowColumnIndex(grid, 0, 1).value.asInstanceOf[Label]
//      dayHeader.text.value shouldBe "Mo 22.07"
//
//      val timeLabel = getNodeByRowColumnIndex(grid, 1, 0).value.asInstanceOf[Label]
//      timeLabel.text.value shouldBe "08:00"
//    }
//
//    "place tasks in the correct cells within the calendar grid" in new Fixture {
//      var grid: GridPane = null
//      runOnFxThread {
//        val rootPane = CalendarViewGuiFactory.updateContent(mockViewModel, None, mockCommonsModule).asInstanceOf[BorderPane]
//        grid = rootPane.center.value.asInstanceOf[GridPane]
//      }
//
//      val taskCellNode = getNodeByRowColumnIndex(grid, 3, 1).value
//      taskCellNode shouldBe a[TextArea]
//      val taskCell = taskCellNode.asInstanceOf[TextArea]
//      taskCell.text.value should include("Morning Stand-up")
//      taskCell.style.value should include("-fx-control-inner-background: #e0f7fa;")
//
//      val emptyCellNode = getNodeByRowColumnIndex(grid, 3, 2).value
//      emptyCellNode shouldBe a[TextArea]
//      val emptyCell = emptyCellNode.asInstanceOf[TextArea]
//      emptyCell.text.value shouldBe empty
//      emptyCell.style.value shouldNot include("-fx-control-inner-background: #e0f7fa;")
//    }
//  }
//
//  // --- Helper functions for navigating the Scene Graph ---
//
//  /** Finds a node in a GridPane by its specific row and column index. */
//  private def getNodeByRowColumnIndex(gridPane: GridPane, row: Int, column: Int): Option[Node] = {
//    import scala.jdk.CollectionConverters._
//    gridPane.children.find { child =>
//      GridPane.getRowIndex(child.delegate) == row && GridPane.getColumnIndex(child.delegate) == column
//    }.map(_.asInstanceOf[Node])
//  }
//
//}