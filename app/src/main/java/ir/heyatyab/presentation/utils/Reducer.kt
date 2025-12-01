package ir.heyatyab.presentation.utils

interface Reducer<State : Reducer.ViewState , Intent : Reducer.ViewIntent , Effect : Reducer.ViewEffect> {
    interface ViewState

    interface ViewIntent

    interface ViewEffect
}