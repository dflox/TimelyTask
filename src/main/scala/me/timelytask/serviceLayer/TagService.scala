package me.timelytask.serviceLayer

import me.timelytask.model.tag.Tag

import java.util.UUID

trait TagService {
  def getTags(userName: String): Seq[Tag]
  def addTag(userName: String, tag: Tag): Unit
  def removeTag(userName: String, uuid: UUID): Unit
  def updateTag(userName: String, tag: Tag): Unit
  def getTag(userName: String, uuid: UUID): Option[Tag]
}
