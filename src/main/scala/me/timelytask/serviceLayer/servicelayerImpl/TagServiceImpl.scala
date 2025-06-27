package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.tag.Tag
import me.timelytask.serviceLayer.TagService

import java.util.UUID

class TagServiceImpl extends TagService {
  
  override def getTags(userName: String): Seq[Tag] = Seq.empty

  override def addTag(userName: String, tag: Tag): Unit = ()

  override def removeTag(userName: String, uuid: UUID): Unit = ()

  override def updateTag(userName: String, tag: Tag): Unit = ()

  override def getTag(userName: String, uuid: UUID): Tag = {
    Tag("Default Tag", "This is a default tag", uuid)
  }
}
