package me.timelytask.core.validators

import me.timelytask.core.StartUpConfig

class StartUpValidatorImpl extends StartUpValidator {
  private lazy val guiValidationStep = new GuiValidationStep()

  private lazy val tuiValidationStep = new TuiValidationStep(Some(guiValidationStep))

  override def validate(startUpConfig: Option[StartUpConfig]): Unit = {
    tuiValidationStep.validate(startUpConfig)
  }
}
