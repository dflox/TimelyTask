package me.timelytask.util.serialization

import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.settings.{CALENDAR, TABLE, UIType}
import me.timelytask.util.serialization.serializer.{JsonSerializationStrategy, XmlSerializationStrategy, YamlSerializationStrategy}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class SerializationRoundTripSpec extends AnyFunSuite with Matchers {

  // These givens make the TypeEncoder and TypeDecoder available implicitly for StartUpConfig
  import _root_.me.timelytask.util.serialization.decoder.given
  import _root_.me.timelytask.util.serialization.encoder.given

  // The sample object used across all tests for consistency
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

  test("XML serialization strategy should correctly serialize and deserialize a StartUpConfig") {
    // Setup
    val strategy: SerializationStrategy = new XmlSerializationStrategy()

    // Action
    val serializedString = strategy.serialize(sampleConfig)
    val deserializedConfig = strategy.deserialize[StartUpConfig](serializedString)

    // Assert
    deserializedConfig should be(Some(sampleConfig))
    serializedString should include("<uiInstances type=\"array\">")
  }

  // --- Testing the `convertArbitraryXml` fallback ---
  test("XML strategy should fail to deserialize ambiguous XML") {
    // Setup
    val strategy: SerializationStrategy = new XmlSerializationStrategy()

    // This XML is ambiguous because the 'uis' field is sometimes a single element
    // and sometimes multiple, which will be decoded to a JSON String and a JSON Array
    // respectively. The StartUpConfig decoder strictly expects a JSON Array, so the
    // case with a single 'uis' element will fail decoding.
    val ambiguousXmlString =
      """
        |<root>
        |  <uiInstances>
        |    <uis>TUI</uis>
        |    <uis>GUI</uis>
        |    <startView>CALENDAR</startView>
        |  </uiInstances>
        |  <uiInstances>
        |    <uis>TUI</uis>
        |    <startView>TABLE</startView>
        |  </uiInstances>
        |</root>
        |""".stripMargin

    // Action
    val deserializedConfig = strategy.deserialize[StartUpConfig](ambiguousXmlString)

    // Assert
    // The deserialization *should* fail and return None because the second
    // UiInstanceConfig produces {"uis": "TUI"}, which cannot be decoded into a List.
    deserializedConfig should be(None)
  }


  test("JSON Deserialization should return None for malformed data") {
    // Setup
    val jsonStrategy: SerializationStrategy = new JsonSerializationStrategy()
    val invalidString = "this is not valid {json}"

    // Action
    val result = jsonStrategy.deserialize[StartUpConfig](invalidString)

    // Assert
    result should be(None)
  }

  test("XML Deserialization should return None for malformed data") {
    // Setup
    val xmlStrategy: SerializationStrategy = new XmlSerializationStrategy()
    // This XML is invalid because of the unclosed tag
    val invalidString = "<root><uiInstances><uis>TUI</uis</uiInstances></root>"

    // Action
    val result = xmlStrategy.deserialize[StartUpConfig](invalidString)

    // Assert
    result should be(None)
  }

  test("SerializationStrategy factory object should return the correct strategy instances") {
    // Setup, Action, Assert
    SerializationStrategy("json") shouldBe a[JsonSerializationStrategy]
    SerializationStrategy("yaml") shouldBe a[YamlSerializationStrategy]
    // The factory should now correctly return an XmlSerializationStrategy
    SerializationStrategy("xml") shouldBe a[XmlSerializationStrategy]

    // The exception should be thrown for a truly unsupported format
    assertThrows[IllegalArgumentException] {
      SerializationStrategy("unsupported-format")
    }
  }
}