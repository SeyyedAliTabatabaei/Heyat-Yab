package ir.heyatyab.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Reducer.ViewState , Intent : Reducer.ViewIntent , Effect : Reducer.ViewEffect>(initialState : State) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state

    protected val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()


    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    abstract fun processIntent(intent : Intent)

    override fun onCleared() {
        super.onCleared()
    }
}