package me.timelytask.serviceLayer

trait FileExportService {
  def exportToFile(userName: String, folderPathWithFileName: String, serializationType: String): Unit
  def importFromFile(userName: String, folderPathWithFileName: String, serializationType: String): Unit
}
