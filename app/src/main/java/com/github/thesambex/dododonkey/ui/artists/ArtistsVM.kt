package com.github.thesambex.dododonkey.ui.artists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.github.thesambex.dododonkey.library.MediaLibrary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArtistsVM @Inject constructor(
    private val mediaLibrary: MediaLibrary
) : ViewModel() {

    var uiState by mutableStateOf(ArtistsUiState())

    fun fetchAll() = viewModelScope.launch(Dispatchers.IO) {
        uiState = uiState.copy(
            artists = mediaLibrary.listArtists().cachedIn(viewModelScope)
        )
    }

}