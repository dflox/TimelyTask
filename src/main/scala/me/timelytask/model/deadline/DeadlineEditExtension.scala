package me.timelytask.model.deadline

import com.github.nscala_time.time.Imports.DateTime

extension (d: Deadline) {
  /**
   * Creates a new Deadline with the specified date.
   *
   * @param date the date to set
   * @return a new Deadline instance with the updated date
   * @note If the initial date is not set, it will be set to the provided date.
   *       If the initial date is set and is after the provided date,
   *       the initial date will be updated to the provided date.
   */
  private[model] def withDate(date: DateTime): Deadline = {
    if (d.initialDate.isEmpty) d.copy(date = date, initialDate = Some(date))
    if( d.initialDate.exists(_.isAfter(date))) {
      d.copy(date = date, initialDate = Some(date))
    }
    else d.copy(date = date)
  }

  /**
   * Creates a new Deadline with the specified completion date.
   *
   * @param completionDate the completion date to set
   * @return a new Deadline instance with the updated completion date
   */
  def withCompletionDate(completionDate: DateTime): Either[String, Deadline] =
    if (completionDate.isBefore(d.date)) {
      Left("Completion date cannot be before the deadline date")
    } else {
      Right(d.copy(completionDate = Some(completionDate)))
    }

  /**
   * Creates a new Deadline with the specified initial date.
   * @param initialDate the initial date to set
   * @return Either[String, Deadline] - Right with updated Deadline if successful,
   *         Left with an error message if the initial date is after the deadline date.
   */
  def withInitialDate(initialDate: DateTime): Either[String, Deadline] = {
    if (initialDate.isAfter(d.date)) {
      Left("Initial date cannot be after the deadline date")
    } else {
      Right(d.copy(initialDate = Some(initialDate)))
    }
  }
}