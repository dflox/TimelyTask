package model
import model.settings.*
import io.circe.generic.auto.*
import scala.xml.XML


case class Config (defaultStartView: StartView, defaultDataFileType: FileType, defaultTheme: Theme) extends XmlSerializable[Config] with YamlSerializable[Config] with Data[Config] {

  val dataType: DataType = DataType.CONFIG

  val XmlEncoder: Config => String = Config => {
    <config>
      <defaultStartView>
        {Config.defaultStartView}
      </defaultStartView>
      <defaultDataFileType>
        {Config.defaultDataFileType}
      </defaultDataFileType>
      <defaultTheme>
        {Config.defaultTheme}
      </defaultTheme>
    </config>.toString()
  }
  val XmlDecoder: String => Config = xmlString => {
    val xml = XML.loadString(xmlString)
    val defaultStartView = (xml \\ "defaultStartView").text
    val defaultDataFileType = (xml \\ "defaultDataFileType").text
    val defaultTheme = (xml \\ "defaultTheme").text
    Config(StartView.fromString(defaultStartView), FileType.fromString(defaultDataFileType), Theme.fromString(defaultTheme))
  }
  val YamlEncoder: Config => String = Config => {
    s"defaultStartView: ${Config.defaultStartView}\ndefaultDataFileType: ${Config.defaultDataFileType}\ndefaultTheme: ${Config.defaultTheme}"
  }
  val YamlDecoder: String => Config = yaml => {
    val defaultStartView = yaml.split("\n")(0).split(":")(1).trim
    val defaultDataFileType = yaml.split("\n")(1).split(":")(1).trim
    val defaultTheme = yaml.split("\n")(2).split(":")(1).trim
    Config(StartView.fromString(defaultStartView), FileType.fromString(defaultDataFileType), Theme.fromString(defaultTheme))
  }
}

object Config {
  val defaultConfig: Config = Config(StartView.CALENDAR, FileType.JSON, Theme.LIGHT)

  implicit val yamlSerializable: YamlSerializable[Config] = new YamlSerializable[Config] {
    override val YamlEncoder: Config => String = Config => {
      s"defaultStartView: ${Config.defaultStartView}\ndefaultDataFileType: ${Config.defaultDataFileType}\ndefaultTheme: ${Config.defaultTheme}"
    }
    override val YamlDecoder: String => Config = yaml => {
      val defaultStartView = yaml.split("\n")(0).split(":")(1).trim
      val defaultDataFileType = yaml.split("\n")(1).split(":")(1).trim
      val defaultTheme = yaml.split("\n")(2).split(":")(1).trim
      Config(StartView.fromString(defaultStartView), FileType.fromString(defaultDataFileType), Theme.fromString(defaultTheme))
    }
  }

  implicit val xmlSerializable: XmlSerializable[Config] = new XmlSerializable[Config] {
    override val dataType: DataType = DataType.CONFIG
    override val XmlEncoder: Config => String = Config => {
      <config>
        <defaultStartView>
          {Config.defaultStartView}
        </defaultStartView>
        <defaultDataFileType>
          {Config.defaultDataFileType}
        </defaultDataFileType>
        <defaultTheme>
          {Config.defaultTheme}
        </defaultTheme>
      </config>.toString()
    }
    override val XmlDecoder: String => Config = xmlString => {
      val xml = XML.loadString(xmlString)
      val defaultStartView = (xml \\ "defaultStartView").text
      val defaultDataFileType = (xml \\ "defaultDataFileType").text
      val defaultTheme = (xml \\ "defaultTheme").text
      Config(StartView.fromString(defaultStartView), FileType.fromString(defaultDataFileType), Theme.fromString(defaultTheme))
    }
  }
}