package com.github.thesambex.dododonkey.ui.home

import kotlinx.serialization.Serializable

sealed class HomeScreenRoute {
    @Serializable
    data object Artists
}