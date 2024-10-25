package model

import com.github.nscala_time.time.Imports.DateTime

case class Deadline(date: DateTime, initialDate: Option[DateTime], completionDate: Option[DateTime]) {
}
object Deadline{
  def fromString(s: String): Deadline = {
    val date = DateTime.parse(s)
    Deadline(date, None, None)
  }
}