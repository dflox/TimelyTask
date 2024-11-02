package view

import model.settings.ViewType

trait ActiveViewObserver{
  def onActiveViewChange(viewType: ViewType): Unit
}

class ActiveViewPublisher{
  private var observers: List[ActiveViewObserver] = List()
  private var activeView: ViewType = ViewType.CALENDAR

  def subscribe(observer: ActiveViewObserver): Unit = {
    observers = observer :: observers
  }
  
  def updateActiveView(viewType: ViewType): Unit = {
    activeView = viewType
    observers.foreach(_.onActiveViewChange(viewType))
  }
  
  def getActiveView: ViewType = activeView
}


