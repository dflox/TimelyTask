import io.circe.{Decoder, Json}
import io.circe.yaml.v12.parser
import me.timelytask.core.{StartUpConfig, UIInstanceConfig}
import me.timelytask.model.settings.UIType
import me.timelytask.util.serialization.{SerializationStrategy, TypeDecoder}

var yamlStr = """uiInstances:
            |- uis:
            |  - uiType: tui
            |serializationType: json""".stripMargin

import me.timelytask.util.serialization.decoder.given
yamlStr

val uiTypeDecoder: Decoder[UIType]= Decoder.forProduct1[UIType, String]("uiType")(s => UIType
  .fromString(s))

val uiInstanceConfigDecoder: Decoder[UIInstanceConfig] = Decoder
  .forProduct1[UIInstanceConfig, List[UIType]]
  ("uis")(typeList => UIInstanceConfig(typeList))(Decoder.decodeList(uiTypeDecoder))

val decoder: Decoder[StartUpConfig] = Decoder
  .forProduct2[StartUpConfig, List[UIInstanceConfig], String]("uiInstances", "serializationType")(
    (uis: List[UIInstanceConfig], serializationType: String) => StartUpConfig(uis,
      serializationType))
  (Decoder.decodeList[UIInstanceConfig](uiInstanceConfigDecoder))

val json = parser.parse(yamlStr)
json.getOrElse(Json.False).as[StartUpConfig](decoder)
given_TypeDecoder_StartUpConfig(json.getOrElse(Json.False))

SerializationStrategy("yaml").deserialize[StartUpConfig](yamlStr)
