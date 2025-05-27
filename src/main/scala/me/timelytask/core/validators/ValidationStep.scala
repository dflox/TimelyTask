package me.timelytask.core.validators

import me.timelytask.core.StartUpConfig

trait ValidationStep(val nextValidator: Option[ValidationStep]) {

  def validate(startUpConfig: Option[StartUpConfig]): Unit = {
    validationProcess(startUpConfig.getOrElse(throwException_StartUpConfigLoadingFailed()))
    nextValidator.foreach(_.validate(startUpConfig))
  }

  protected def validationProcess(startUpConfig: StartUpConfig): Unit

  private def throwException_StartUpConfigLoadingFailed(): Nothing = {
    throw new Exception("StartUpConfig could not be loaded!")
  }
}