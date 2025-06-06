package me.timelytask.core.validation.startupValidatorImpl

import me.timelytask.core.StartUpConfig
import me.timelytask.core.validation.StartUpValidator
import me.timelytask.core.validation.startupValidatorImpl.GuiValidationStep

class StartUpValidatorImpl extends StartUpValidator {
  private lazy val guiValidationStep = new GuiValidationStep()

  private lazy val tuiValidationStep = new TuiValidationStep(Some(guiValidationStep))

  override def validate(startUpConfig: Option[StartUpConfig]): Unit = {
    tuiValidationStep.validate(startUpConfig)
  }
}
