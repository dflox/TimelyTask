package model.fileio

import model.settings.*
import model.Config
import scala.xml.XML

trait ConfigEncoders
  extends XmlSerializable[Config]
    with YamlSerializable[Config]
    with Data[Config] {

  // DataType for Config
  val dataType: DataType = DataType.CONFIG

  // Helper for XML Encoding
  def encodeToXml(config: Config): String = {
    <config>
      <defaultStartView>
        {config.defaultStartView}
      </defaultStartView>
      <defaultDataFileType>
        {config.defaultDataFileType}
      </defaultDataFileType>
      <defaultTheme>
        {config.defaultTheme}
      </defaultTheme>
    </config>.toString()
  }

  // Helper for XML Decoding
  def decodeFromXml(xmlString: String): Config = {
    val xml = XML.loadString(xmlString)
    val defaultStartView = (xml \\ "defaultStartView").text
    val defaultDataFileType = (xml \\ "defaultDataFileType").text
    val defaultTheme = (xml \\ "defaultTheme").text
    Config(
      StartView.fromString(defaultStartView),
      FileType.fromString(defaultDataFileType),
      Theme.fromString(defaultTheme)
    )
  }

  // Helper for YAML Encoding
  def encodeToYaml(config: Config): String = {
    s"defaultStartView: ${config.defaultStartView}\ndefaultDataFileType: ${config.defaultDataFileType}\ndefaultTheme: ${config.defaultTheme}"
  }

  // Helper for YAML Decoding
  def decodeFromYaml(yaml: String): Config = {
    val lines = getLines(yaml)
    Config(
      StartView.fromString(lines(0)),
      FileType.fromString(lines(1)),
      Theme.fromString(lines(2))
    )
  }

  // Implement XmlSerializable methods
  override val XmlEncoder: Config => String = encodeToXml
  override val XmlDecoder: String => Config = decodeFromXml

  // Implement YamlSerializable methods
  override val YamlEncoder: Config => String = encodeToYaml
  override val YamlDecoder: String => Config = decodeFromYaml
}