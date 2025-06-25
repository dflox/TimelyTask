package me.timelytask.util.extensions

extension[A](list: List[A])
  /**
   * Replaces exactly one element that satisfies the filter predicate with the provided new element.
   * Throws an exception if zero or more than one element match the predicate.
   */
  def replaceOne(filter: A => Boolean, newElement: A): List[A] =
    val (before, matchedAndAfter) = list.span(!filter(_))
    matchedAndAfter match
      case head :: tail if filter(head) =>
        val remaining = tail.dropWhile(filter)
        if tail.exists(filter) then
          throw new IllegalArgumentException("More than one element matches the filter.")
        before ++ (newElement :: remaining)
      case _ =>
        throw new NoSuchElementException("No element matching the filter was found.")
