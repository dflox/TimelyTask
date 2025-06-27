package me.timelytask.model.config

import me.timelytask.model.settings.{FileType, KeymapConfig, Theme, ViewType}

extension (config: Config) {
  def withKeymaps(keymaps: Map[ViewType, KeymapConfig]): Config = {
    config.copy(keymaps = keymaps)
  }

  def withKeymap(viewType: ViewType, keymapConfig: KeymapConfig): Unit = {
    config.copy(keymaps = config.keymaps.updatedWith(viewType)(k => Some(keymapConfig)))
  }

  def withGlobalKeymap(keymapConfig: KeymapConfig): Config = {
    config.copy(globalKeymap = keymapConfig)
  }

  def withStartView(startView: ViewType): Config = {
    config.copy(startView = startView)
  }

  def withDataFileType(dataFileType: FileType): Config = {
    config.copy(dataFileType = dataFileType)
  }

  def withTheme(theme: Theme): Config = {
    config.copy(theme = theme)
  }
}