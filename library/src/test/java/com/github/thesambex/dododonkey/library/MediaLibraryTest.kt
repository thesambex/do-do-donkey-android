package com.github.thesambex.dododonkey.library

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.eq
import org.mockito.kotlin.isNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MediaLibraryTest {
    private val mockContentResolver: ContentResolver = mock()

    private lateinit var mediaLibrary: MediaLibrary

    @Before
    fun setup() {
        mediaLibrary = PhoneLibrary(mockContentResolver)
    }

    @Test
    fun `should list artists`() = runTest {
        val mockCursor = mock<Cursor>()
        whenever(
            mockContentResolver.query(
                eq(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI),
                eq(
                    arrayOf(
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST
                    )
                ),
                isNull(),
                isNull(),
                eq("${MediaStore.Audio.Artists.ARTIST} ASC")
            )
        ).thenReturn(mockCursor)

        whenever(mockCursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID))
            .thenReturn(0)

        whenever(mockCursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST))
            .thenReturn(1)

        whenever(mockCursor.moveToNext())
            .thenReturn(true, true, true, false)

        whenever(mockCursor.getLong(0))
            .thenReturn(1L, 2L, 3L)

        whenever(mockCursor.getString(1))
            .thenReturn("Artist 1", "Artist 2", "Artist 3")

        val artist = mediaLibrary.listArtists()

        Assert.assertEquals(3, artist.size)
    }

}