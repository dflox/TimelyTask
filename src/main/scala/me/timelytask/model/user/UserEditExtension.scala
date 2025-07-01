package me.timelytask.model.user

extension (user: User){
  def withName(name: String): User = {
    if(name.isEmpty) {
      throw new IllegalArgumentException("Name cannot be empty")
    }
    user.copy(name = name)
  }
}