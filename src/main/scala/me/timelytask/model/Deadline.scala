package me.timelytask.model

import com.github.nscala_time.time.Imports.DateTime

case class Deadline(date: DateTime, initialDate: Option[DateTime], completionDate: Option[DateTime]) {  
  override def toString: String = s"Deadline: $date, Initial Date: $initialDate, Completion Date: $completionDate"
}