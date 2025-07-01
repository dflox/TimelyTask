import com.github.nscala_time.time.Imports.*
import io.circe.Decoder
import me.timelytask.model.Model
import me.timelytask.model.task.Task
import me.timelytask.model.user.User
import me.timelytask.repository.TaskRepository
import me.timelytask.repository.repositoryImpl.{SqliteTaskRepository, SqliteUserRepository}
import me.timelytask.util.serialization.SerializationStrategy
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.viewImpl.tui.CalendarViewStringFactory
import me.timelytask.util.serialization.decoder.given
import me.timelytask.util.serialization.encoder.given
import simplesql.DataSource

import scala.collection.mutable

//var testModel = Model.emptyModel
//var serializationStrategy = SerializationStrategy.apply("xml")
//var testModelSerialized = serializationStrategy.serialize(testModel)
//var testModelAfterSerialization = serializationStrategy.deserialize[Model](testModelSerialized)

val map: mutable.Map[String, Any] = mutable.Map.empty

map("test") = "testValue"

map("test")
map.get("test1")
