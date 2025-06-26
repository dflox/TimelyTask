package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.serviceLayer.FileExportService

class FileExportServiceImpl extends FileExportService {

  override def exportToFile(userName: String, folderPathWithFileName: String, serializationType: String): Unit = ???

  override def importFromFile(userName: String, folderPathWithFileName: String, serializationType: String): Unit = ???
}
