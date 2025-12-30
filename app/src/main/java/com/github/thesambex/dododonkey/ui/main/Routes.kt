package com.github.thesambex.dododonkey.ui.main

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home : Route

}