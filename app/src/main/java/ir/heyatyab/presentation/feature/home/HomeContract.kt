package ir.heyatyab.presentation.feature.home

import androidx.compose.runtime.Immutable
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.presentation.utils.Reducer

@Immutable
data class HomeUiState (
    val events : List<Event.Response.Data> = emptyList() ,
    val showLoading : Boolean = false
) : Reducer.ViewState

@Immutable
sealed class HomeIntent : Reducer.ViewIntent{
    data object LoadEvents : HomeIntent()
    data class ShowEventDetails(val event : Event.Response.Data) : HomeIntent()

}

@Immutable
sealed class HomeEffect : Reducer.ViewEffect {
    data class ShowEventDetails(val event : Event.Response.Data) : HomeEffect()

}