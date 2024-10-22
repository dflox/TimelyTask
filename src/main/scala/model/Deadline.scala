package model

import com.github.nscala_time.time.Imports.DateTime

case class Deadline(date: DateTime, initialDate: Option[DateTime], completionDate: Option[DateTime]) {
  def this(date: DateTime) = this(date, None, None)
  def this(date: DateTime, initialDate: DateTime) = this(date, Some(initialDate), None)
  def this(date: DateTime, initialDate: DateTime, completionDate: DateTime) = this(date, Some(initialDate), Some(completionDate))
  
  override def toString: String = s"Deadline: $date, Initial Date: $initialDate, Completion Date: $completionDate"
}
object Deadline {
  def fromPrintString(deadlineString: String): Deadline = {
    val date = deadlineString.split(",")(0).split(":")(1).trim
    val initialDate = deadlineString.split(",")(1).split(":")(1).trim
    val completionDate = deadlineString.split(",")(2).split(":")(1).trim
    new Deadline(DateTime.parse(date), DateTime.parse(initialDate), DateTime.parse(completionDate))
  }
  def fromString(date: String): Deadline = new Deadline(DateTime.parse(date))
  def fromString(date: String, initialDate: String): Deadline = new Deadline(DateTime.parse(date), DateTime.parse(initialDate))
  def fromString(date: String, initialDate: String, completionDate: String): Deadline = new Deadline(DateTime.parse(date), DateTime.parse(initialDate), DateTime.parse(completionDate))
}