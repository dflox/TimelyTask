// src/main/scala/me/timelytask/model/Builder.scala
package me.timelytask.model.builder

trait Builder[T](defaultInstance: T) {
  def build(): T
}