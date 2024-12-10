package me.timelytask.view.viewmodel.elemts

class TextField(description: String, textInput: String) extends Focusable{
  def this(description: String) = this(description, "")
  
  def input(text: String): TextField = {
    new TextField(description, text)
  }
}
