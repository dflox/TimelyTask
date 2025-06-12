package me.timelytask.model

import com.github.nscala_time.time.Imports.DateTime
import me.timelytask.util.extensions.equalsWithComparison

case class Deadline(date: DateTime, initialDate: Option[DateTime],
                    completionDate: Option[DateTime]) {
  override def toString: String = s"Deadline: $date, Initial Date: $initialDate, Completion " +
    s"Date: $completionDate"

  override def equals(obj: Any): Boolean = obj match {
    case that: Deadline =>
      this.date.isEqual(that.date) &&
      this.initialDate.equalsWithComparison(that.initialDate, _.isEqual(_)) &&
      this.completionDate.equalsWithComparison(that.completionDate, _.isEqual(_))
    case _ => false
  }
}