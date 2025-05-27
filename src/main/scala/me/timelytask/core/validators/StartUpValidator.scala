package me.timelytask.core.validators

import me.timelytask.core.StartUpConfig;

trait StartUpValidator {
    def validate(startUpConfig: Option[StartUpConfig]): Unit
}