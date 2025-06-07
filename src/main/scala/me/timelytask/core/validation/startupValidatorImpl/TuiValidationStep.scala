package me.timelytask.core.validation.startupValidatorImpl

import me.timelytask.core.StartUpConfig
import me.timelytask.model.settings.UIType.TUI

class TuiValidationStep(nextValidator: Option[ValidationStep] = None)
  extends ValidationStep(nextValidator) {

  override protected def validationProcess(startUpConfig: StartUpConfig): Unit = {
    if (startUpConfig.uiInstances.flatMap(i => i.uis.filter(ui => ui.eq(TUI))).length > 1)
      throw new Exception("Only one TUI instance is allowed!")
  }
}
