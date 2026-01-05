package com.github.thesambex.dododonkey.ui.artists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ArtistsLifecycle(viewModel: ArtistsVM) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()

    when (state) {
        Lifecycle.State.DESTROYED -> {}
        Lifecycle.State.INITIALIZED -> {}
        Lifecycle.State.CREATED -> {}
        Lifecycle.State.STARTED -> {}
        Lifecycle.State.RESUMED -> viewModel.fetchAll()

    }
}