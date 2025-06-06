package me.timelytask.core.validation.startupValidatorImpl

import com.sun.javafx.application.PlatformImpl
import me.timelytask.core.StartUpConfig

import scala.util.Try

class GuiValidationStep(nextValidator: Option[ValidationStep] = None)
  extends ValidationStep(nextValidator) {



  override protected def validationProcess(startUpConfig: StartUpConfig): Unit = {
    Try[Unit] {
      PlatformImpl.startup(() => {})
      PlatformImpl.setImplicitExit(false)
    }.recover {
      case e: Exception => throw new Exception("GUI validation failed: " + e.getMessage)
    }
  }
}
