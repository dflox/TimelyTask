package me.timelytask.util.serialization

import com.github.nscala_time.time.Imports.DateTime
import io.circe.{Decoder, Encoder}
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.config.Config
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.priority.Priority
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, EventTypeId, FileType, KeymapConfig, TASKEdit, Theme, UIType, ViewType}
import me.timelytask.model.state.{ClosedState, DeletedState, OpenState, TaskState}
import me.timelytask.model.tag.Tag
import me.timelytask.model.task.Task
import me.timelytask.model.utility.Key
import me.timelytask.util.serialization.encoder.*
import me.timelytask.util.serialization.decoder.*
import org.scalatest.wordspec.AnyWordSpec
import scalafx.scene.paint.Color

import scala.collection.immutable.HashSet
//TODO: test failures of decoding for coverage
class EncoderDecoderExtensionSpec extends AnyWordSpec {
  "The ViewTypeEncoder and ViewTypeDecoder" should {
    "correctly encode and decode a ViewType" in {
      val viewTypes = ViewType.getAll

      viewTypes.foreach { viewType =>
        val encoded = Encoder.viewType(viewType)
        val decoded = encoded.as[ViewType](Decoder.viewType)

        assert(decoded.isRight, s"Failed to decode $viewType")
        assert(decoded.contains(viewType), s"Expected $viewType but got $decoded")
      }
    }
  }

  "The viewTypeKeyEncoder and viewTypeKeyDecoder" should {
    "correctly encode and decode a ViewType key" in {
      val viewTypes = ViewType.getAll

      viewTypes.foreach { viewType =>
        val encoded = Encoder.viewTypeKeyEncoder(viewType)
        val decoded = Decoder.viewTypeKeyDecoder(encoded)

        assert(decoded.isDefined, s"Failed to decode ViewType for $viewType")
        assert(decoded.contains(viewType), s"Expected $viewType but got $decoded")
      }
    }
  }

  "The uiTypeEncoder and uiTypeDecoder" should {
    "correctly encode and decode a UIType" in {
      val uiTypes = UIType.values

      uiTypes.foreach { uiType =>
        val encoded = Encoder.uiType(uiType)
        val decoded = encoded.as[UIType](Decoder.uiType)

        assert(decoded.isRight, s"Failed to decode $uiType")
        assert(decoded.contains(uiType), s"Expected $uiType but got $decoded")
      }
    }
  }

  "The uiInstanceConfigEncoder and uiInstanceConfigDecoder" should {
    "correctly encode and decode a UiInstanceConfig" in {
      val uiInstanceConfig = UiInstanceConfig(List(UIType.GUI, UIType.TUI), CALENDAR)

      val encoded = Encoder.uiInstanceConfig(uiInstanceConfig)
      val decoded = encoded.as[UiInstanceConfig](Decoder.uiInstanceConfig)

      assert(decoded.isRight, s"Failed to decode UiInstanceConfig")
      assert(decoded.contains(uiInstanceConfig), s"Expected $uiInstanceConfig but got $decoded")
    }
  }

  "The startUpConfigEncoder and startUpConfigDecoder" should {
    "correctly encode and decode a StartUpConfig" in {
      val uiInstanceConfig = UiInstanceConfig(List(UIType.GUI, UIType.TUI), CALENDAR)
      val uiInstanceConfig2 = UiInstanceConfig(List(UIType.GUI), TASKEdit)
      val startUpConfig = StartUpConfig(List(uiInstanceConfig, uiInstanceConfig2))

      val encoded = Encoder.startUpConfig(startUpConfig)
      val decoded = encoded.as[StartUpConfig](Decoder.startUpConfig)

      assert(decoded.isRight, s"Failed to decode StartUpConfig")
      assert(decoded.contains(startUpConfig), s"Expected $startUpConfig but got $decoded")
    }
  }

  "The keyEncoder and keyDecoder" should {
    "correctly encode and decode a Key" in {
      val keys = Key.keyMap.keys.toSeq

      keys.foreach { keyStr =>
        val key = Key.fromString(keyStr)
        val encoded = Encoder.key(key)
        val decoded = Decoder.key(encoded)

        assert(decoded.isDefined, s"Failed to decode Key for $keyStr")
        assert(decoded.contains(key), s"Expected $key but got $decoded")
      }
    }
  }

  "The eventTypeIdEncoder and eventTypeIdDecoder" should {
    "correctly encode and decode an EventTypeId" in {
      val eventTypeIds = List(
        EventTypeId("testEvent1"),
        EventTypeId("testEvent2"),
        EventTypeId("testEvent3")
      )

      eventTypeIds.foreach { eventTypeId =>
        val encoded = Encoder.eventTypeId(eventTypeId)
        val decoded = encoded.as[EventTypeId](Decoder.eventTypeId)

        assert(decoded.isRight, s"Failed to decode $eventTypeId")
        assert(decoded.contains(eventTypeId), s"Expected $eventTypeId but got $decoded")
      }
    }
  }

  "The keymapConfigEncoder and keymapConfigDecoder" should {
    "correctly encode and decode a KeymapConfig" in {
      val keymapConfig = KeymapConfig(Map(
        Key.fromString("Ctrl+C") -> EventTypeId("copy"),
        Key.fromString("Ctrl+V") -> EventTypeId("paste")
      ))

      val encoded = Encoder.keymapConfig(keymapConfig)
      val decoded = encoded.as[KeymapConfig](Decoder.keymapConfig)

      assert(decoded.isRight, s"Failed to decode KeymapConfig")
      assert(decoded.contains(keymapConfig), s"Expected $keymapConfig but got $decoded")
    }
  }

  "The fileTypeEncoder and fileTypeDecoder" should {
    "correctly encode and decode a FileType" in {
      val fileTypes = FileType.values

      fileTypes.foreach { fileTypeStr =>
        val encoded = Encoder.fileType(fileTypeStr)
        val decoded = encoded.as[FileType](Decoder.fileType)

        assert(decoded.isRight, s"Failed to decode FileType for $fileTypeStr")
        assert(decoded.contains(fileTypeStr), s"Expected $fileTypeStr but got $decoded")
      }
    }
  }

  "The themeEncoder and themeDecoder" should {
    "correctly encode and decode a Theme" in {
      val themes = Theme.values

      themes.foreach { themeStr =>
        val encoded = Encoder.theme(themeStr)
        val decoded = encoded.as[Theme](Decoder.theme)

        assert(decoded.isRight, s"Failed to decode Theme for $themeStr")
        assert(decoded.contains(themeStr), s"Expected $themeStr but got $decoded")
      }
    }
  }

  "The configEncoder and configDecoder" should {
    "correctly encode and decode a Config" in {
      val keymaps: Map[ViewType, KeymapConfig] = Map(
        CALENDAR -> KeymapConfig(Map(Key.fromString("Ctrl+C") -> EventTypeId("copy"))),
        TASKEdit -> KeymapConfig(Map(Key.fromString("Ctrl+V") -> EventTypeId("paste")))
      )
      val globalKeymap = KeymapConfig(Map(Key.fromString("Ctrl+S") -> EventTypeId("save")))
      val startView = CALENDAR
      val dataFileType = FileType.JSON
      val theme = Theme.LIGHT

      val config = Config(keymaps, globalKeymap, startView, dataFileType, theme)

      val encoded = Encoder.config(config)
      val decoded = encoded.as[Config](Decoder.config)

      assert(decoded.isRight, s"Failed to decode Config")
      assert(decoded.contains(config), s"Expected $config but got $decoded")
    }
  }

  "The tagEncoder and tagDecoder" should {
    "correctly encode and decode a Tag" in {
      val tag = Tag("TestTag", "This is a test tag", java.util.UUID.randomUUID())

      val encoded = Encoder.tag(tag)
      val decoded = encoded.as[Tag](Decoder.tag)

      assert(decoded.isRight, s"Failed to decode Tag")
      assert(decoded.contains(tag), s"Expected $tag but got $decoded")
    }
  }

  "The hashSetEncoder and hashSetDecoder" should {
    "correctly encode and decode a HashSet of any type" in {
      val tags = HashSet(
        Tag("Tag1", "Description1"),
        Tag("Tag2", "Description2"),
        Tag("Tag3", "Description3")
      )

      val encoded = Encoder.encodeHashSet(Encoder.tag)(tags)
      val decoded = encoded.as[HashSet[Tag]](Decoder.decodeHashSet(Decoder.tag))

      assert(decoded.isRight, s"Failed to decode HashSet of Tags")
      assert(decoded.contains(tags), s"Expected $tags but got $decoded")
    }
  }

  "The colorEncoder and colorDecoder" should {
    "correctly encode and decode a Color" in {
      val color = Color(0.2, 0.3, 0.4, 1.0)

      val encoded = Encoder.color(color)
      val decoded = encoded.as[Color](Decoder.color)

      assert(decoded.isRight, s"Failed to decode Color")
      assert(decoded.contains(color), s"Expected $color but got $decoded")
    }
  }

  "The taskStateEncoder and taskStateDecoder" should {
    "correctly encode and decode a TaskState" in {
      val openState = OpenState("Open", "Task is open", Color.Green)
      val closedState = ClosedState("Closed", "Task is closed", Color.Red)
      val deletedState = DeletedState("Deleted", "Task is deleted", Color.Gray)

      val states = List(openState, closedState, deletedState)
      states.foreach { state =>
        val encoded = Encoder.taskState(state)
        val decoded = encoded.as[TaskState](Decoder.taskState)

        assert(decoded.isRight, s"Failed to decode TaskState for ${state.name}")
        assert(decoded.contains(state), s"Expected ${state.name} but got $decoded")
      }
    }
  }

  "The priorityEncoder and priorityDecoder" should {
    "correctly encode and decode a TaskState priority" in {
      val priorities = List(
        Priority("Low", "Low priority", 5, Color.Green, 30, true, java.util.UUID.randomUUID()),
        Priority("Medium", "Medium priority", 10, Color.Yellow, 20, true, java.util.UUID.randomUUID()),
        Priority("High", "High priority", 15, Color.Red, 10, false, java.util.UUID.randomUUID())
      )

      priorities.foreach { priority =>
        val encoded = Encoder.priority(priority)
        val decoded = encoded.as[Priority](Decoder.priority)

        assert(decoded.isRight, s"Failed to decode priority for $priority")
        assert(decoded.contains(priority), s"Expected $priority but got $decoded")
      }
    }
  }

  "The datetimeEncoder and datetimeDecoder" should {
    "correctly encode and decode a DateTime" in {
      val dateTime = DateTime.now()

      val encoded = Encoder.dateTime(dateTime)
      val decoded = encoded.as[DateTime](Decoder.dateTime)

      assert(decoded match {
        case Right(dt) => dt.isEqual(dateTime)
        case Left(error) =>
          fail(s"Decoding failed: $error\nExpected $dateTime but got $decoded")
      }, s"Expected $dateTime but got $decoded")
    }
  }


  "The periodEncoder and periodDecoder" should {
    "correctly encode and decode a Period" in {
      val period = com.github.nscala_time.time.Imports.Period.days(5)

      val encoded = Encoder.period(period)
      val decoded = encoded.as[com.github.nscala_time.time.Imports.Period](Decoder.period)

      assert(decoded.isRight, s"Failed to decode Period")
      assert(decoded.contains(period), s"Expected $period but got $decoded")
    }
  }

  "The deadlineEncoder and deadlineDecoder" should {
    "correctly encode and decode a Deadline" in {
      val deadline = Deadline(DateTime.lastDay(), Some(DateTime.lastWeek()), Some(DateTime.tomorrow()))

      val encoded = Encoder.deadline(deadline)
      val decoded = encoded.as[Deadline](Decoder.deadline)

      assert(decoded.isRight, s"Failed to decode Deadline")
      assert(decoded.contains(deadline), s"Expected $deadline but got $decoded")
    }
  }

  "The taskEncoder and taskDecoder" should {
    "correctly encode and decode a Task" in {
      val task = Task.exampleTask

      val encoded = Encoder.task(task)
      val decoded = encoded.as[Task](Decoder.task)

      assert(decoded.isRight, s"Failed to decode Task")
      assert(decoded.contains(task), s"Expected $task but got $decoded")
    }
  }
  
  "The modelEncoder and modelDecoder" should {
    "correctly encode and decode a Model" in {
      val model = Model.emptyModel.copy(tasks = List(Task.exampleTask), tags = HashSet(Tag("ExampleTag", "An example tag")))

      val encoded = Encoder.model(model)
      val decoded = encoded.as[Model](Decoder.model)

      assert(decoded.isRight, s"Failed to decode Model")
      assert(decoded.contains(model), s"Expected $model but got $decoded")
    }
  }
}
