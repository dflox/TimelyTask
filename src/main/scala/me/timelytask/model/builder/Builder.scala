// src/main/scala/me/timelytask/model/Builder.scala
package me.timelytask.model.builder

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.{Deadline, Task}
import me.timelytask.model.state.*

import java.util.UUID
import scala.collection.immutable.HashSet

trait Builder[T](defaultInstance: T) {
  def build(): T
}