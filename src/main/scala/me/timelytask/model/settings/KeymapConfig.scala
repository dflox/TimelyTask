package me.timelytask.model.settings

import me.timelytask.model.utility.Key;

case class KeymapConfig(mappings: Map[Key, EventTypeId])