package me.timelytask.controller

trait PersistenceController {

  /**
   * Initializes the persistence controller.
   */
  private[controller] def init(): Unit

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
  def saveModel(folderPath: Option[String], fileName: Option[String]): Boolean

  /**
   * Loads a model from a specified file.
   *
   * @param folderPathWithFileName The full path to the file including the folder and file name.
   * @return Boolean indicating whether the load operation was successful.
   * @throws IllegalStateException    if the serialization strategy is not set.
   * @throws IllegalArgumentException if the file does not exist or is 
   *                                  empty.
   */
  def loadModel(folderPathWithFileName: String): Boolean
  
  /**
   * Loads a model for a specific user.
   * If the model is not found in the database, it will be created.
   * @param userName The name of the user whose model is to be loaded.
   */
  private[controller] def provideModelFromDB(userName: String): Unit
  
  /**
   * Sets the serialization type for the persistence controller.
   * @param serializationType the type of serialization to use, e.g., "json", "xml" or "yaml".
   * @return Boolean indicating whether the serialization type was set successfully.
   */
  def setSerializationType(serializationType: String): Boolean
}
