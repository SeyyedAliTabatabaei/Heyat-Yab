package ir.heyatyab.presentation.feature.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heyatyab.domain.model.base.RemoteResultEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.usecase.GetEventListUseCase
import ir.heyatyab.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEventListUseCase: GetEventListUseCase
) : BaseViewModel<HomeUiState, HomeIntent, HomeEffect>(
    initialState = HomeUiState()
) {
    private val TAG = "HomeViewModel"

    override fun processIntent(intent: HomeIntent) {
        when(intent) {
            HomeIntent.LoadEvents -> getList()
            is HomeIntent.ShowEventDetails -> sendEffect(HomeEffect.ShowEventDetails(intent.event))
        }
    }

    private fun getList() {
        getEventListUseCase(Event.Request())
            .onEach { result ->
                Log.i(TAG, "getList: $result")
                when(result) {
                    RemoteResultEntity.Loading -> _state.update { it.copy(showLoading = true) }
                    is RemoteResultEntity.NetworkError -> {

                    }
                    RemoteResultEntity.NoInternet -> {

                    }
                    is RemoteResultEntity.Success -> _state.update { it.copy(
                        showLoading = false ,
                        events = result.data.data ?: emptyList()
                    ) }
                }
            }.launchIn(viewModelScope)
    }


}