package util

case class tuiHelper(){
  def createSpace(length: Int): String = {
    " " * length
  }

  def createLine(length: Int): String = {
    "-" * length
  }
}
