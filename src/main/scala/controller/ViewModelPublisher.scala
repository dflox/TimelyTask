package controller

import view.viewmodel.ViewModel

trait ViewModelObserver {
  def onViewModelChange(newViewModel: ViewModel): Unit
}

class ViewModelPublisher(private var viewModel: ViewModel) {
  private var observers: List[ViewModelObserver] = List()

  def subscribe(observer: ViewModelObserver): Unit = {
    observers = observer :: observers
  }

  def updateViewModel(newViewModel: ViewModel): Unit = {
    viewModel = newViewModel
    observers.foreach(_.onViewModelChange(newViewModel))
  }
  
  def getCurrentViewModel: ViewModel = viewModel
}
