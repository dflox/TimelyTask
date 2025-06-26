package me.timelytask.serviceLayer

import me.timelytask.model.config.Config

trait ConfigService {
  def getConfig(userName: String): Config
  def updateConfig(userName: String, config: Config): Unit
  def resetConfig(userName: String): Unit
  private[serviceLayer] def deleteConfig(userName: String): Unit
}
