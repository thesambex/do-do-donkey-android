package com.github.thesambex.dododonkey.di

import android.content.Context
import com.github.thesambex.dododonkey.library.MediaLibrary
import com.github.thesambex.dododonkey.library.PhoneLibrary
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Library {

    @Provides
    @Singleton
    fun providesPhoneLibrary(@ApplicationContext context: Context): MediaLibrary = PhoneLibrary(context.contentResolver)

}