package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.Model
import me.timelytask.serviceLayer.{FileExportService, ServiceModule}
import me.timelytask.util.FileIO
import me.timelytask.util.serialization.SerializationStrategy
import org.joda.time.DateTime
import me.timelytask.util.serialization.encoder.given
import me.timelytask.util.serialization.decoder.given

class FileExportServiceImpl(serviceModule: ServiceModule) extends FileExportService {

  private var serializers: Map[String, SerializationStrategy] = Map.empty

  private def getSerializer(serializationType: String): SerializationStrategy = {
    serializers.get(serializationType) match {
      case Some(serializer) => serializer
      case None =>
        serializers = serializers + (serializationType -> SerializationStrategy(serializationType))
        serializers(serializationType)
    }
  }

  override def exportToFile(userName: String,
                            folderPathWithFileName: String,
                            serializationType: String): Unit = {
    val serializer = getSerializer(serializationType)
    val serializedModel = serializer.serialize(serviceModule.modelService
      .getModel(userName))

    FileIO.writeToFile(folderPathWithFileName.concat(f".${serializer.fileExtension}"),
      serializedModel)
  }

  override def importFromFile(userName: String,
                              folderPathWithFileName: String,
                              serializationType: String): Unit = {
    val serializer = getSerializer(serializationType)
    val serializedModel = FileIO.readFromFile(folderPathWithFileName)

    if (serializedModel.isEmpty) throw new IllegalArgumentException(
      s"File not found or empty: $folderPathWithFileName"
    )

    serializer.deserialize[Model](serializedModel.get) match {
      case Some(model) =>
        serviceModule.modelService.saveModel(userName, model)
      case None =>
        throw new IllegalArgumentException(
          s"Failed to deserialize model from file: $folderPathWithFileName"
        )
    }
  }
}
