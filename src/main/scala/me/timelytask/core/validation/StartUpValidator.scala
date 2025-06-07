package me.timelytask.core.validation

import me.timelytask.core.StartUpConfig;

trait StartUpValidator {
    def validate(startUpConfig: Option[StartUpConfig]): Unit
}