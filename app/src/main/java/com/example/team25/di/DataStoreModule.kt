package com.example.team25.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.team25.TokensProto.Tokens
import com.example.team25.data.database.TokenSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val DATA_STORE_FILE_NAME = "location_service_state"
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {context.preferencesDataStoreFile(DATA_STORE_FILE_NAME)}
        )
    }

    private val Context.tokenDataStore: DataStore<Tokens> by dataStore(
        fileName = "tokens.pb",
        serializer = TokenSerializer
    )

    @Provides
    @Singleton
    @TokenDataStore
    fun provideTokenDataStore(
        @ApplicationContext context: Context
    ): DataStore<Tokens> {
        return context.tokenDataStore
    }
}
