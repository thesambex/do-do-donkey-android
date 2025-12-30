package com.github.thesambex.dododonkey.library

import android.content.Context
import android.provider.MediaStore
import com.github.thesambex.dododonkey.domain.artists.Artist

class PhoneLibrary(private val context: Context) : MediaLibrary {

    override suspend fun listArtists(): List<Artist> {
        val artists = mutableListOf<Artist>()

        context.contentResolver.query(
            MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST
            ),
            null,
            null,
            "${MediaStore.Audio.Artists.ARTIST} ASC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                artists.add(Artist(id, name))
            }
        }

        return artists
    }

}