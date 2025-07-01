package me.timelytask.util.serialization

import io.circe.{Decoder, Encoder, Json}
import io.circe.generic.auto.*
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.settings.{CALENDAR, TABLE, UIType}
import me.timelytask.util.serialization.serializer.{JsonSerializationStrategy, XmlSerializationStrategy, YamlSerializationStrategy}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class SerializationRoundTripSpec extends AnyFunSuite with Matchers {

  // Import the application's specific TypeEncoder/TypeDecoder definitions
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


  case class PrimitivesTestCase(isGood: Boolean, count: Int, comment: Option[String])

  case class ArbitraryTarget(name: String, _attributes: Map[String, String], _value: String)

  case class InferredTypes(isActive: Boolean, score: Int)

  // Bridge for PrimitivesTestCase
  given TypeEncoder[PrimitivesTestCase] = (p: PrimitivesTestCase) => summon[Encoder[PrimitivesTestCase]].apply(p)
  given TypeDecoder[PrimitivesTestCase] = (j: Json) => summon[Decoder[PrimitivesTestCase]].decodeJson(j).toOption

  // Bridge for ArbitraryTarget
  given TypeEncoder[ArbitraryTarget] = (p: ArbitraryTarget) => summon[Encoder[ArbitraryTarget]].apply(p)
  given TypeDecoder[ArbitraryTarget] = (j: Json) => summon[Decoder[ArbitraryTarget]].decodeJson(j).toOption

  // Bridge for InferredTypes
  given TypeEncoder[InferredTypes] = (p: InferredTypes) => summon[Encoder[InferredTypes]].apply(p)
  given TypeDecoder[InferredTypes] = (j: Json) => summon[Decoder[InferredTypes]].decodeJson(j).toOption


  test("JSON serialization strategy should correctly serialize and deserialize a StartUpConfig") {
    val strategy: SerializationStrategy = new JsonSerializationStrategy()
    val serializedString = strategy.serialize(sampleConfig)
    val deserializedConfig = strategy.deserialize[StartUpConfig](serializedString)
    deserializedConfig should be(Some(sampleConfig))
  }

  test("YAML serialization strategy should correctly serialize and deserialize a StartUpConfig") {
    val strategy: SerializationStrategy = new YamlSerializationStrategy()
    val serializedString = strategy.serialize(sampleConfig)
    val deserializedConfig = strategy.deserialize[StartUpConfig](serializedString)
    deserializedConfig should be(Some(sampleConfig))
  }

  test("XML serialization strategy should correctly serialize and deserialize a StartUpConfig") {
    val strategy: SerializationStrategy = new XmlSerializationStrategy()
    val serializedString = strategy.serialize(sampleConfig)
    val deserializedConfig = strategy.deserialize[StartUpConfig](serializedString)
    deserializedConfig should be(Some(sampleConfig))
    serializedString should include("<uiInstances type=\"array\">")
  }

  test("XML strategy should correctly round-trip primitive types (Boolean, Number, Null)") {
    val strategy = new XmlSerializationStrategy()
    val sample = PrimitivesTestCase(isGood = true, count = 123, comment = None)

    val serialized = strategy.serialize(sample)
    val deserialized = strategy.deserialize[PrimitivesTestCase](serialized)

    serialized should include("<isGood type=\"boolean\">true</isGood>")
    serialized should include("<count type=\"number\">123</count>")
    serialized should include("<comment type=\"null\"/>")
    deserialized should be(Some(sample))
  }

  test("XML strategy should handle arbitrary XML with attributes and mixed content") {
    val strategy = new XmlSerializationStrategy()
    val xmlWithAttrsAndMixedContent =
      """
        |<root id="item-123" status="active">
        |  <name>Mixed Content Test</name>
        |  The text value of the root.
        |</root>
        |""".stripMargin

    val expected = ArbitraryTarget(
      name = "Mixed Content Test",
      _attributes = Map("id" -> "item-123", "status" -> "active"),
      _value = "The text value of the root."
    )

    val deserialized = strategy.deserialize[ArbitraryTarget](xmlWithAttrsAndMixedContent)
    deserialized should be(Some(expected))
  }

  test("XML strategy should infer primitive types from arbitrary XML") {
    val strategy = new XmlSerializationStrategy()
    val xmlWithInferrableTypes =
      """
        |<root>
        |  <isActive>true</isActive>
        |  <score>99</score>
        |</root>
        |""".stripMargin

    val expected = InferredTypes(isActive = true, score = 99)

    val deserialized = strategy.deserialize[InferredTypes](xmlWithInferrableTypes)
    deserialized should be(Some(expected))
  }


  test("XML strategy should fail to deserialize ambiguous XML") {
    val strategy: SerializationStrategy = new XmlSerializationStrategy()
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
    val deserializedConfig = strategy.deserialize[StartUpConfig](ambiguousXmlString)
    deserializedConfig should be(None)
  }

  test("XML Deserialization should return None for malformed data") {
    val xmlStrategy: SerializationStrategy = new XmlSerializationStrategy()
    val invalidString = "<root><uiInstances><uis>TUI</uis</uiInstances></root>"
    val result = xmlStrategy.deserialize[StartUpConfig](invalidString)
    result should be(None)
  }

  test("SerializationStrategy factory object should return the correct strategy instances") {
    SerializationStrategy("json") shouldBe a[JsonSerializationStrategy]
    SerializationStrategy("yaml") shouldBe a[YamlSerializationStrategy]
    SerializationStrategy("xml") shouldBe a[XmlSerializationStrategy]

    assertThrows[IllegalArgumentException] {
      SerializationStrategy("unsupported-format")
    }
  }
}