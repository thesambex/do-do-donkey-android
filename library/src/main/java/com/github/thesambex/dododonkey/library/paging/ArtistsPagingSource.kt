package com.github.thesambex.dododonkey.library.paging

import android.content.ContentResolver
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.thesambex.dododonkey.domain.artists.Artist

class ArtistsPagingSource(private val contentResolver: ContentResolver) :
    PagingSource<Int, Artist>() {

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        val page = params.key ?: 0
        val pageSize = params.loadSize

        return try {
            val artists = mutableListOf<Artist>()

            val cursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val bundle = Bundle().apply {
                    putInt(ContentResolver.QUERY_ARG_OFFSET, page * pageSize)
                    putInt(ContentResolver.QUERY_ARG_LIMIT, pageSize)

                    putStringArray(
                        ContentResolver.QUERY_ARG_SORT_COLUMNS,
                        arrayOf(MediaStore.Audio.Artists.ARTIST)
                    )

                    putInt(
                        ContentResolver.QUERY_ARG_SORT_DIRECTION,
                        ContentResolver.QUERY_SORT_DIRECTION_ASCENDING
                    )
                }

                contentResolver.query(
                    MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                    arrayOf(
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST
                    ),
                    bundle,
                    null
                )
            } else {
                contentResolver.query(
                    MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                    arrayOf(
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST
                    ),
                    null,
                    null,
                    "${MediaStore.Audio.Artists.ARTIST} ASC LIMIT $pageSize OFFSET ${page * pageSize}"
                )
            }

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)
                val nameColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)

                while (it.moveToNext()) {
                    artists.add(
                        Artist(
                            id = it.getLong(idColumn),
                            name = it.getString(nameColumn)
                        )
                    )
                }
            }

            LoadResult.Page(
                data = artists,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (artists.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}