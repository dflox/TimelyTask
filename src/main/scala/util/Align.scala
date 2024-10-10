package util

enum Align(stringRepresentation: String):
  override def toString: String = stringRepresentation
  case c extends Align("Center")
  case l extends Align("Left")
  case r extends Align("Right")

