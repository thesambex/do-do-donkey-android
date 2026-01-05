package com.github.thesambex.dododonkey.library

import android.content.ContentResolver
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.thesambex.dododonkey.domain.artists.Artist
import com.github.thesambex.dododonkey.library.paging.ArtistsPagingSource
import kotlinx.coroutines.flow.Flow

class PhoneLibrary(private val contentResolver: ContentResolver) : MediaLibrary {

    override suspend fun listArtists(): Flow<PagingData<Artist>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30
            ),
            pagingSourceFactory = {
                ArtistsPagingSource(contentResolver)
            }
        ).flow
    }

}