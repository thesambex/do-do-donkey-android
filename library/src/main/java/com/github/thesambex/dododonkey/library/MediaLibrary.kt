package com.github.thesambex.dododonkey.library

import com.github.thesambex.dododonkey.domain.artists.Artist

interface MediaLibrary {
    suspend fun listArtists(): List<Artist>
}