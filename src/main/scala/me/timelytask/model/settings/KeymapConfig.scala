package me.timelytask.model.settings

import me.timelytask.model.utility.Key;

case class KeymapConfig(mappings: Map[Key, EventTypeId]) {
  def addMapping(keymapConfig: KeymapConfig): KeymapConfig = {
    val diff = keymapConfig.mappings.keySet.diff(mappings.keySet)
    val newMappings = mappings ++ keymapConfig.mappings.view.filterKeys(diff.contains)
    KeymapConfig(newMappings)
  }
}