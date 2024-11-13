package me.timelytask.view.viewmodel.elemts

import me.timelytask.view.viewmodel.elemts.FocusDirection.*

class FocusElementCollection(elements: Array[Array[Focusable]], var focusedElement: Focusable)
  extends Focusable {
  def this(width: Int, height: Int) = this(Array.ofDim[Focusable](width, height), null)

  val width: Int = elements.length
  val height: Int = elements(0).length

  val elementsList: Array[Focusable] = elements.flatten

  def setElement(x: Int, y: Int, element: Focusable): Unit = {
    elements(x)(y) = element
  }

  def setFocusToElement(x: Int, y: Int): FocusElementCollection = {
    new FocusElementCollection(elements, elements(x)(y))
  }

  def setFocusToElement(element: Focusable): Unit = {
    focusedElement = element
  }

  def moveFocus(direction: FocusDirection): Boolean = {
    val (x, y) = getFocusedElementPosition
    val newElement = direction match {
      case UP => getNextElementUpwards(x, y)
      case DOWN => getNextElementDownwards(x, y)
      case LEFT => getNearestElement(x - 1, y)
      case RIGHT => getNearestElement(x + 1, y)
      case BEGIN => getNextElementDownwards(0, 0)
      case END => getNextElementUpwards(width - 1, height - 1)
    }
    newElement match {
      case Some(element) =>
        focusedElement = element
        true
      case None => false
    }
  }

  def getNextElementDownwards(x: Int, y: Int): Option[Focusable] = {
    elementsList.drop(xyToIndex(x, y)).find(_ != null)
  }

  def getNextElementUpwards(x: Int, y: Int): Option[Focusable] = {
    elementsList.take(xyToIndex(x, y)).findLast(_ != null)
  }

  def getNearestElement(x: Int, y: Int): Option[Focusable] = {
    val pos = xyToIndex(x, y)
    if pos < 0 || pos >= elementsList.length then return None

    val maxDistance = math.max(pos, elementsList.length - pos - 1)
    (0 to maxDistance).flatMap { offset =>
      // Check within bounds on both sides of the position
      Seq(
        if (pos - offset >= 0) elementsList(pos - offset) else null, // Left side
        if (pos + offset < elementsList.length) elementsList(pos + offset) else null // Right side
      ).find(_ != null) // Find the first non-null in this sequence
    }.headOption // Get the first non-null element found
  }

  def xyToIndex(x: Int, y: Int): Int = {
    x * (height - 1) + y
  }

  def getFocusedElementPosition: (Int, Int) = {
    val result = (for {
      i <- 0 until height
      j <- 0 until width
      if elements(i)(j) == focusedElement
    } yield (i, j)).headOption.getOrElse((-1, -1))
    result
  }
}