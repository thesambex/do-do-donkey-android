package com.github.thesambex.dododonkey.ui.artists

import androidx.paging.PagingData
import com.github.thesambex.dododonkey.domain.artists.Artist
import kotlinx.coroutines.flow.Flow

data class ArtistsUiState(
    val artists: Flow<PagingData<Artist>>? = null
)