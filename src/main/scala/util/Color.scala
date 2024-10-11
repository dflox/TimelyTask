package util

enum Color(hex: String):
  override def toString: String = hex
  case accent1 extends Color("#FFC107")
