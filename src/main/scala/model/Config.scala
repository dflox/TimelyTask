package model
import model.settings.*
import io.circe.generic.auto.*
import model.fileio._

case class Config (defaultStartView: StartView, defaultDataFileType: FileType, defaultTheme: Theme) extends ConfigEncoders {
}

object Config extends ConfigEncoders {
  val defaultConfig: Config = Config(StartView.CALENDAR, FileType.JSON, Theme.LIGHT)

  implicit val yamlSerializable: YamlSerializable[Config] = new YamlSerializable[Config] {
    override val YamlEncoder: Config => String = encodeToYaml
    override val YamlDecoder: String => Config = decodeFromYaml
  }

  implicit val xmlSerializable: XmlSerializable[Config] = new XmlSerializable[Config] {
    override val dataType: DataType = DataType.CONFIG
    override val XmlEncoder: Config => String = encodeToXml
    override val XmlDecoder: String => Config = decodeFromXml
  }
}