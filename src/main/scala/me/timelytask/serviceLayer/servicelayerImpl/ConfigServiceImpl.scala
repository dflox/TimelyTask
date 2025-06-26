package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.config.Config
import me.timelytask.serviceLayer.ConfigService

class ConfigServiceImpl extends ConfigService {

  override def getConfig(userName: String): Config = ???

  override def updateConfig(userName: String, config: Config): Unit = ???

  override def resetConfig(userName: String): Unit = ???

  override private[serviceLayer] def deleteConfig(userName: String): Unit = ???
}
