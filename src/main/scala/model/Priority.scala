package model

import java.awt.Color

case class Priority(id: Int, rank: Int, name: String, color: Color, description: String,
                    daysPreDeadline: Int, postponable: Boolean) {
}
