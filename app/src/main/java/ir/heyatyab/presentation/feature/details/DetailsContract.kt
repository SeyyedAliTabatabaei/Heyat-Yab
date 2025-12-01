package ir.heyatyab.presentation.feature.details

import androidx.compose.runtime.Immutable
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.presentation.utils.Reducer

@Immutable
data class DetailsUiState (
    val event : Event.Response.Data ?= null ,
    val showLoading : Boolean = false
) : Reducer.ViewState

@Immutable
sealed class DetailsIntent : Reducer.ViewIntent{
    data class LoadEvent(val id : Long) : DetailsIntent()

}

@Immutable
sealed class DetailsEffect : Reducer.ViewEffect {

}