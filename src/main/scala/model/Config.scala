package model

import model.settings.*
import io.circe.generic.auto.*

case class Config (defaultStartView: StartView, defaultDataFileType: FileType, defaultTheme: Theme) {
}