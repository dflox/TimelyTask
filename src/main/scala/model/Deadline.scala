package model

import com.github.nscala_time.time.Imports.DateTime

case class Deadline(date: DateTime, initialDate: Option[DateTime], completionDate: Option[DateTime]) {
  def this(date: DateTime) = this(date, None, None)
  def this(date: DateTime, initialDate: DateTime) = this(date, Some(initialDate), None)
  def this(date: DateTime, initialDate: DateTime, completionDate: DateTime) = this(date, Some(initialDate), Some(completionDate))
}
