package me.timelytask.util.serialization.serializer

import io.circe.{Json, JsonNumber, JsonObject}
import me.timelytask.util.serialization.{SerializationStrategy, TypeDecoder, TypeEncoder}
import scala.util.Try
import scala.xml.{Attribute => XmlAttr, Elem, Null, Node, PrettyPrinter, Text, TopScope, XML}

/**
 * A serialization strategy for converting objects to and from XML.
 *
 * This implementation bridges the gap between Circe's JSON abstract syntax tree (AST)
 * and Scala's built-in XML support (`scala.xml`).
 *
 * Serialization:
 * It creates a self-describing XML format by adding a `type` attribute to each element
 * (`<user type="object">...`) to ensure a lossless round-trip conversion.
 *
 * Deserialization:
 * It first looks for the `type` attribute for a reliable conversion. If the attribute
 * is missing, it falls back to a best-effort conversion logic suitable for arbitrary XML.
 */
class XmlSerializationStrategy extends SerializationStrategy {
  override val fileExtension: String = "xml"

  // Constants for special attribute and key names used in the conversion process.
  private val TYPE_ATTR = "type"          // Attribute to store the original JSON type (string, number, etc.).
  private val ATTRS_KEY = "_attributes"   // JSON key for storing XML attributes to avoid name collisions.
  private val VALUE_KEY = "_value"        // JSON key for a mixed-content element's text value.
  private val ARRAY_ITEM_LABEL = "item"   // Default XML tag name for items in a JSON array.

  // A constant for the number zero, created using Circe's public `fromString` API.
  // We can safely call .get as "0" is guaranteed to be a valid number.
  private val ZERO_JSON_NUMBER: JsonNumber = JsonNumber.fromString("0").get

  /**
   * Serializes a given object into a pretty-printed XML string.
   */
  override def serialize[T](obj: T)(using typeEncoder: TypeEncoder[T]): String = {
    val json = typeEncoder(obj)
    val xml = jsonToXml(json, "root")
    new PrettyPrinter(120, 2).format(xml)
  }

  /**
   * Deserializes an XML string into an object of type T.
   */
  override def deserialize[T](str: String)(using typeDecoder: TypeDecoder[T]): Option[T] = {
    Try(XML.loadString(str))
      .map(xmlToJson)
      .toOption
      .flatMap(typeDecoder.apply)
  }

  /**
   * Recursively converts a Circe Json value to a scala.xml.Node.
   * This method embeds the `type` attribute in each element to preserve type information,
   * enabling a reliable deserialization process.
   *
   * @param json The Json value to convert.
   * @param label The tag name for the resulting XML element.
   * @return A scala.xml.Node representing the Json.
   */
  private def jsonToXml(json: Json, label: String): Node = {
    json.fold(
      jsonNull    = Elem(null, label, XmlAttr(TYPE_ATTR, Text("null"), Null), TopScope, true),
      jsonBoolean = b => Elem(null, label, XmlAttr(TYPE_ATTR, Text("boolean"), Null), TopScope, true, Text(b.toString)),
      jsonNumber  = n => Elem(null, label, XmlAttr(TYPE_ATTR, Text("number"), Null), TopScope, true, Text(n.toString)),
      jsonString  = s => Elem(null, label, XmlAttr(TYPE_ATTR, Text("string"), Null), TopScope, true, Text(s)),
      jsonArray   = arr => Elem(null, label, XmlAttr(TYPE_ATTR, Text("array"), Null), TopScope, true, arr.map(j => jsonToXml(j, ARRAY_ITEM_LABEL))*),
      jsonObject  = obj => Elem(null, label, XmlAttr(TYPE_ATTR, Text("object"), Null), TopScope, true, obj.toVector.map { case (k, v) => jsonToXml(v, k) }*)
    )
  }

  /**
   * Converts a scala.xml.Node to a Circe Json value.
   * It prioritizes the `type` attribute for conversion. If not present, it falls
   * back to a heuristic-based conversion for generic XML.
   */
  private def xmlToJson(node: Node): Json = {
    val attributes = node.attributes.asAttrMap
    val nodeType = attributes.get(TYPE_ATTR)

    nodeType match {
      // Primary strategy: Use the explicit type attribute for perfect conversion.
      case Some("object")  => Json.fromJsonObject(collectObjectFields(node))
      case Some("array")   => Json.fromValues(node.child.filterNot(_.isAtom).map(xmlToJson))
      case Some("string")  => Json.fromString(node.text)
      case Some("number")  => Json.fromJsonNumber(JsonNumber.fromString(node.text).getOrElse(ZERO_JSON_NUMBER))
      case Some("boolean") => Json.fromBoolean(node.text.toBoolean)
      case Some("null")    => Json.Null
      // Fallback strategy: No type attribute found, so handle as arbitrary XML.
      case _            => convertArbitraryXml(node)
    }
  }

  /**
   * Provides a best-effort conversion for an arbitrary XML node that lacks a `type` attribute.
   * Conventions used:
   * - Attributes are collected into a nested `_attributes` object.
   * - If an element has attributes/children AND text, the text is stored in a `_value` field.
   * - If an element only contains text, its type is inferred (boolean, number, or string).
   */
  private def convertArbitraryXml(node: Node): Json = {
    val attributesAsJson = Json.fromFields(node.attributes.asAttrMap.map { case (k, v) => k -> Json.fromString(v) })
    val childrenAsJsonObject = collectObjectFields(node)

    val text = node.child.collect { case t: Text => t.text.trim }.mkString
    val hasAttributes = attributesAsJson.asObject.exists(_.nonEmpty)

    var fields = Map.empty[String, Json]
    if (hasAttributes) {
      fields += (ATTRS_KEY -> attributesAsJson)
    }
    fields ++= childrenAsJsonObject.toMap

    if (text.nonEmpty) {
      if (fields.isEmpty) {
        // Node is a simple element with only text content, e.g., <name>John</name>.
        inferJsonFromText(text)
      } else {
        // Node is a mixed-content element, e.g., <p id="1">some <b>bold</b> text</p>.
        fields += (VALUE_KEY -> Json.fromString(text))
        Json.fromJsonObject(JsonObject.fromMap(fields))
      }
    } else {
      // Node has no text content, just attributes and/or child elements.
      Json.fromJsonObject(JsonObject.fromMap(fields))
    }
  }

  /**
   * Groups child nodes by their tag name to correctly form a JsonObject.
   * This handles the common XML pattern where multiple elements with the same name
   * under a single parent should be represented as a JSON array.
   * e.g., <items><item>A</item><item>B</item></items> -> { "items": { "item": ["A", "B"] } }
   */
  private def collectObjectFields(node: Node): JsonObject = {
    val children = node.child.filterNot(_.isAtom)
    val fields = children.groupBy(_.label).map {
      case (label, nodes) if nodes.size > 1 =>
        label -> Json.fromValues(nodes.map(xmlToJson))
      case (label, nodes) =>
        label -> xmlToJson(nodes.head)
    }
    JsonObject.fromMap(fields)
  }

  /**
   * Infers a JSON type from a string value when no explicit `type` attribute is available.
   * It attempts to parse as Boolean, then Number, and finally defaults to String.
   */
  private def inferJsonFromText(text: String): Json = {
    if (text.equalsIgnoreCase("true") || text.equalsIgnoreCase("false")) {
      Json.fromBoolean(text.toBoolean)
    } else {
      JsonNumber.fromString(text) match {
        case Some(jsonNumber) => Json.fromJsonNumber(jsonNumber)
        case None             => Json.fromString(text) // Fallback to string if not a valid number
      }
    }
  }
}