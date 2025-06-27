package me.timelytask.util.extensions

extension[T](option: Option[T]) {
  def equalsWithComparison(other: Option[T], comparator: (t1: T, t2: T) => Boolean): Boolean = {
    (option, other) match {
      case (Some(a), Some(b)) => comparator(a, b)
      case (None, None) => true
      case _ => false
    }
  }
}