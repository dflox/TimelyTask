package me.timelytask.view.views

import me.timelytask.model.settings.{CALENDAR, KeymapConfig, ViewType}
import me.timelytask.model.utility.{Key, MoveUp, Space}
import me.timelytask.util.Publisher
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.views.commonsModules.ViewTypeCommonsModule
import org.mockito.Mockito.{never, times, verify, when}
import org.mockito.ArgumentMatchers.any
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class ViewSpec extends AnyWordSpec with Matchers with MockitoSugar {

  trait TestViewModel extends ViewModel[CALENDAR.type, TestViewModel]
  type TestRenderType = String

  class TestableView(
                      commons: ViewTypeCommonsModule[CALENDAR.type, TestViewModel],
                      val dialogFactoryOverride: DialogFactory[TestRenderType]
                    ) extends View[CALENDAR.type, TestViewModel, TestRenderType](commons) {
    override def render: (TestRenderType, ViewType) => Unit = (_, _) => {}
    override def update(viewModel: Option[TestViewModel]): Boolean = true
    override def dialogFactory: DialogFactory[TestRenderType] = dialogFactoryOverride

    def setKeymapForTest(newKeymap: Keymap[CALENDAR.type, TestViewModel]): Unit = {
      this.keymap = Some(newKeymap)
    }
  }

  trait Fixture {
    val mockCommonsModule: ViewTypeCommonsModule[CALENDAR.type, TestViewModel] =
      mock[ViewTypeCommonsModule[CALENDAR.type, TestViewModel]]
    val mockViewModelPublisher: Publisher[TestViewModel] = mock[Publisher[TestViewModel]]
    val mockKeymap: Keymap[CALENDAR.type, TestViewModel] = mock[Keymap[CALENDAR.type, TestViewModel]]
    val mockViewModel: TestViewModel = mock[TestViewModel]
    val mockDialogFactory: DialogFactory[TestRenderType] = mock[DialogFactory[TestRenderType]]

    when(mockCommonsModule.viewModelPublisher).thenReturn(mockViewModelPublisher)
    when(mockCommonsModule.registerKeymapUpdater(any[KeymapConfig => Unit]())).thenAnswer(_ => {})

    val view: View[CALENDAR.type, TestViewModel, TestRenderType] =
      new TestableView(mockCommonsModule, mockDialogFactory)
  }

  "The View trait" when {

    "initialized" should {
      "register a keymap updater and a view model listener" in new Fixture {
        view.init()
        verify(mockCommonsModule, times(1)).registerKeymapUpdater(any[KeymapConfig => Unit]())
        verify(mockViewModelPublisher, times(1)).addListener(view.update)
      }
    }

    "handling key presses" should {

      "interact with the focused element when Space is pressed" in new Fixture {
        val updatedViewModel = mock[TestViewModel]
        when(mockViewModelPublisher.getValue).thenReturn(Some(mockViewModel))
        when(mockViewModel.interact(any())).thenReturn(Some(updatedViewModel))

        val result = view.handleKey(Some(Space))

        result shouldBe true
        verify(mockViewModel, times(1)).interact(any())
        verify(mockViewModelPublisher, times(1)).update(Some(updatedViewModel), any[Option[Any]])
      }

      "delegate other keys to the keymap if it exists" in new Fixture {
        view.asInstanceOf[TestableView].setKeymapForTest(mockKeymap)

        val testKey = Some(MoveUp)
        when(mockKeymap.handleKey(testKey)).thenReturn(true)

        val result = view.handleKey(testKey)

        result shouldBe true
        verify(mockKeymap, times(1)).handleKey(testKey)
      }

      "throw an exception if a key is handled without a keymap" in new Fixture {
        val testKey = Some(MoveUp)

        val exception = intercept[Exception] {
          view.handleKey(testKey)
        }
        exception.getMessage shouldBe "Keymap not found"
      }
    }
  }
}