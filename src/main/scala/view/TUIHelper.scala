package view

case class TUIHelper(){
  def createSpace(length: Int): String = {
    " " * length
  }

  def createLine(length: Int): String = {
    "-" * length
  }
}
