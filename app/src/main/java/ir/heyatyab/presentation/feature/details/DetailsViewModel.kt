package ir.heyatyab.presentation.feature.details

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heyatyab.domain.model.base.RemoteResultEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.model.remote.EventDetails
import ir.heyatyab.domain.usecase.GetEventDetailsUseCase
import ir.heyatyab.domain.usecase.GetEventListUseCase
import ir.heyatyab.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getEventDetailsUseCase: GetEventDetailsUseCase
) : BaseViewModel<DetailsUiState, DetailsIntent, DetailsEffect>(
    initialState = DetailsUiState()
) {
    private val TAG = "DetailsViewModel"

    override fun processIntent(intent: DetailsIntent) {
        when(intent) {
            is DetailsIntent.LoadEvent -> getEvent(intent.id)
        }
    }

    private fun getEvent(id : Long) {
        getEventDetailsUseCase(EventDetails.Request(
            id = id
        )).onEach { result ->
                Log.i(TAG, "getEvent: $result")
            when(result) {
                RemoteResultEntity.Loading -> _state.update { it.copy(showLoading = true) }
                is RemoteResultEntity.NetworkError -> {
                }
                RemoteResultEntity.NoInternet -> {
                }
                is RemoteResultEntity.Success -> _state.update { it.copy(
                    showLoading = false ,
                    event = result.data.data
                ) }
            }
        }.launchIn(viewModelScope)
    }


}