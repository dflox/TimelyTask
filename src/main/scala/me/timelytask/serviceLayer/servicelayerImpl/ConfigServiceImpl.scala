package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.config.Config
import me.timelytask.serviceLayer.{ConfigService, ServiceModule}

class ConfigServiceImpl(serviceModule: ServiceModule) extends ConfigService {

  override def getConfig(userName: String): Config = Config.default

  override def updateConfig(userName: String, config: Config): Unit = serviceModule.updateService
    .updateConfig(userName, Config.default)  

  override def resetConfig(userName: String): Unit = serviceModule.updateService.updateConfig(userName, Config.default)

  override private[serviceLayer] def deleteConfig(userName: String): Unit = ()
}
