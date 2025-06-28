package me.timelytask.view.events.container.contailerImpl

import com.github.nscala_time.time.Imports.{Period, richInt}
import me.timelytask.core.CoreModule
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.model.utility.TimeSelection
import me.timelytask.util.Publisher
import me.timelytask.view.events.EventHandler
import me.timelytask.view.events.event.Event
import me.timelytask.view.viewmodel.CalendarViewModel
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.{reset, verify, when}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

class CalendarEventContainerSpec extends AnyWordSpec
  with MockitoSugar
  with Matchers
  with BeforeAndAfterEach {

  // Mocks
  private val mockViewModelPublisher = mock[Publisher[CalendarViewModel]]
  private val mockActiveViewPublisher = mock[Publisher[ViewType]]
  private val mockEventHandler = mock[EventHandler]
  private val mockCoreModule = mock[CoreModule]

  private var container: CalendarEventContainerImpl = _

  // Helpers
  private val eventCaptor: ArgumentCaptor[Event[?]] = ArgumentCaptor.forClass(classOf[Event[?]])
  private val listenerCaptor: ArgumentCaptor[Option[Model] => Unit] = ArgumentCaptor.forClass(classOf[Option[Model] => Unit])

  override def beforeEach(): Unit = {
    reset(mockViewModelPublisher, mockActiveViewPublisher, mockEventHandler, mockCoreModule)
    container = new CalendarEventContainerImpl(
      mockViewModelPublisher,
      mockActiveViewPublisher,
      mockEventHandler,
      mockCoreModule
    )
  }

  "The CalendarEventContainerImpl" should {

    "when its listener updates the model" should {

      "create a new ViewModel with default TimeSelection if none exists" in {
        // Setup
        container.init()
        verify(mockCoreModule).registerModelListener(listenerCaptor.capture())
        val listener = listenerCaptor.getValue

        // Setup for: No existing ViewModel
        when(mockViewModelPublisher.getValue).thenReturn(None)
        val newModel = mock[Model]

        // Action
        listener(Some(newModel))

        // Assert
        val viewModelCaptor = ArgumentCaptor.forClass(classOf[Option[CalendarViewModel]])
        verify(mockViewModelPublisher).update(viewModelCaptor.capture())

        val capturedVm = viewModelCaptor.getValue.get
        capturedVm.model should be(newModel)
        capturedVm.timeSelection should be(TimeSelection.defaultTimeSelection)
      }

      "create a new ViewModel preserving TimeSelection if one exists" in {
        // Setup
        container.init()
        verify(mockCoreModule).registerModelListener(listenerCaptor.capture())
        val listener = listenerCaptor.getValue

        // Setup for: An existing ViewModel with a custom TimeSelection
        val existingTimeSelection = TimeSelection.defaultTimeSelection.addDayCount(5).get
        val existingVm = CalendarViewModel(existingTimeSelection, mock[Model])
        when(mockViewModelPublisher.getValue).thenReturn(Some(existingVm))
        val newModel = mock[Model]

        // Action
        listener(Some(newModel))

        // Assert
        val viewModelCaptor = ArgumentCaptor.forClass(classOf[Option[CalendarViewModel]])
        verify(mockViewModelPublisher).update(viewModelCaptor.capture())

        val capturedVm = viewModelCaptor.getValue.get
        capturedVm.model should be(newModel)
        capturedVm.timeSelection should be(existingTimeSelection)
      }
    }

    "when handling time-shifting events" should {

      def testPeriodChange(methodToCall: () => Unit, expectedPeriod: Period): Unit = {
        // Setup
        val mockTimeSelection = mock[TimeSelection]
        val mockVm = mock[CalendarViewModel]
        when(mockVm.timeSelection).thenReturn(mockTimeSelection)
        when(mockViewModelPublisher.getValue).thenReturn(Some(mockVm))

        // Action
        methodToCall()

        verify(mockEventHandler).handle(eventCaptor.capture())
        val capturedEvent = eventCaptor.getValue
        capturedEvent.call

        // Assert
        verify(mockTimeSelection).+(expectedPeriod)
      }

      "handle a 'nextDay' event by adding 1 day" in {
        testPeriodChange(() => container.nextDay(), 1.days)
      }

      "handle a 'previousDay' event by subtracting 1 day" in {
        testPeriodChange(() => container.previousDay(), (-1).days)
      }

      "handle a 'nextWeek' event by adding 1 week" in {
        testPeriodChange(() => container.nextWeek(), 1.weeks)
      }

      "handle a 'previousWeek' event by subtracting 1 week" in {
        testPeriodChange(() => container.previousWeek(), (-1).weeks)
      }
    }
  }
}