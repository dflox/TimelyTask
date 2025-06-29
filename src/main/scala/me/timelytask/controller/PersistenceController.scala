package me.timelytask.controller

trait PersistenceController {

  /**
   * Saves the current model to a file.
   *
   * @param folderPath Optional path to the folder where the model will be saved.
   * @param fileName   Optional name of the file to save the model to. If not provided, a default
   *                   name will be used.
   * @return Boolean indicating whether the save operation was successful.
   * @throws IllegalStateException if the serialization strategy is not set.
   * @note If the `folderPath` is not provided, the model will be saved in the current working directory.
   */
  def saveModel(userToken: String,
                folderPath: Option[String] = None,
                fileName: Option[String] = None, 
                serializationType: String): Boolean

  /**
   * Loads a model from a specified file.
   *
   * @param folderPathWithFileName The full path to the file including the folder and file name.
   * @return Boolean indicating whether the load operation was successful.
   * @throws IllegalStateException    if the serialization strategy is not set.
   * @throws IllegalArgumentException if the file does not exist or is 
   *                                  empty.
   */
  def loadModel(userToken: String, folderPathWithFileName: String, serializationType: String)
  : Boolean
  
  /**
   * Loads a model for a specific user.
   * If the model is not found in the database, it will be created.
   * @param userName The name of the user whose model is to be loaded.
   */
  private[controller] def provideModelFromDB(userName: String): Unit
}
