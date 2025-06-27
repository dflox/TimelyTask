package me.timelytask.util.extensions.hashSet

import scala.collection.immutable.HashSet

extension[A](hashSet: HashSet[A]){
  def replaceOne(filter: A => Boolean, newElement: A): HashSet[A] = {
    val (toReplace, others) = hashSet.partition(filter)
    if (toReplace.isEmpty) {
      hashSet + newElement
    } else {
      others + newElement
    }
  }
}