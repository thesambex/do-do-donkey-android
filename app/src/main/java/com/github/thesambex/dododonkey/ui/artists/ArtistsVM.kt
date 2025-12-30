package com.github.thesambex.dododonkey.ui.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun fetchAll() = viewModelScope.launch(Dispatchers.IO) {
        val artists = mediaLibrary.listArtists()
        artists.forEach { artist ->
            Timber.tag("Artists").i("Nome: %s", artist.name)
        }
    }

}