package me.timelytask.model.deadline

import com.github.nscala_time.time.Imports.DateTime
import me.timelytask.util.extensions.equalsWithComparison

/**
 * Deadline class represents a deadline with a specific date, an optional initial date,
 * @param date the deadline date
 * @param initialDate an optional initial date for the deadline
 * @param completionDate an optional completion date for the deadline
 */
case class Deadline(date: DateTime, initialDate: Option[DateTime],
                    completionDate: Option[DateTime]) {
  /**
   * Checks if the deadline is completed.
   *
   * @return true if the completion date is defined, false otherwise
   */
  def isCompleted: Boolean = this.completionDate.isDefined

  /**
   * Checks if the deadline is overdue.
   *
   * @return true if the deadline date is before the current date and not completed, false otherwise
   */
  def isOverdue: Boolean = !this.isCompleted && this.date.isBefore(DateTime.now())

  /**
   * @inheritdoc
   */
  override def toString: String = s"Deadline: $date, Initial Date: $initialDate, Completion " +
    s"Date: $completionDate"

  /**
   * @inheritdoc
   */
  override def equals(obj: Any): Boolean = obj match {
    case that: Deadline =>
      this.date.isEqual(that.date) &&
      this.initialDate.equalsWithComparison(that.initialDate, _.isEqual(_)) &&
      this.completionDate.equalsWithComparison(that.completionDate, _.isEqual(_))
    case _ => false
  }
}