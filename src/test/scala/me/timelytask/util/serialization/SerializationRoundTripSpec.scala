package me.timelytask.util.serialization

import me.timelytask.model.settings.{CALENDAR, TABLE}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import me.timelytask.util.serialization.serializer.{JsonSerializationStrategy, YamlSerializationStrategy}
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.settings.UIType


class SerializationRoundTripSpec extends AnyFunSuite with Matchers {

  import _root_.me.timelytask.util.serialization.decoder.given
  import _root_.me.timelytask.util.serialization.encoder.given

  val sampleConfig: StartUpConfig = StartUpConfig(
    uiInstances = List(
      UiInstanceConfig(
        uis = List(UIType.TUI, UIType.GUI),
        startView = CALENDAR
      ),
      UiInstanceConfig(
        uis = List(UIType.TUI),
        startView = TABLE
      )
    )
  )

  test("JSON serialization strategy should correctly serialize and deserialize a StartUpConfig") {
    // Setup
    val strategy: SerializationStrategy = new JsonSerializationStrategy()

    // Action
    val serializedString = strategy.serialize(sampleConfig)
    val deserializedConfig = strategy.deserialize[StartUpConfig](serializedString)

    // Assert
    deserializedConfig should be(Some(sampleConfig))
  }

  test("YAML serialization strategy should correctly serialize and deserialize a StartUpConfig") {
    // Setup
    val strategy: SerializationStrategy = new YamlSerializationStrategy()

    // Action
    val serializedString = strategy.serialize(sampleConfig)
    val deserializedConfig = strategy.deserialize[StartUpConfig](serializedString)

    // Assert
    deserializedConfig should be(Some(sampleConfig))
  }

  test("Deserialization should return None for malformed data") {
    // Setup
    val jsonStrategy: SerializationStrategy = new JsonSerializationStrategy()
    val invalidString = "this is not valid {json}"

    // Action
    val result = jsonStrategy.deserialize[StartUpConfig](invalidString)

    // Assert
    result should be(None)
  }

  test("SerializationStrategy factory object should return the correct strategy instances") {
    // Setup, Action, Assert
    SerializationStrategy("json") shouldBe a[JsonSerializationStrategy]
    SerializationStrategy("yaml") shouldBe a[YamlSerializationStrategy]

    assertThrows[IllegalArgumentException] {
      SerializationStrategy("xml")
    }
  }
}