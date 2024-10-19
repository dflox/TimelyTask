package model

import model.settings.DataType

trait Data[T] {
  val dataType: DataType
}
