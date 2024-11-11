package com.kakaotech.team25M.ui.status.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationStateManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val IS_SERVICE_RUNNING = booleanPreferencesKey("IS_SERVICE_RUNNING")
        private val ACCOMPANY_ID = longPreferencesKey("ACCOMPANY_ID")
    }

    suspend fun storeId(id:Long, isServiceRunning:Boolean) {
        dataStore.edit {preferences ->
            preferences[IS_SERVICE_RUNNING] = isServiceRunning
            preferences[ACCOMPANY_ID] = id
        }
    }

    val runningServiceFlow: Flow<Boolean?> = dataStore.data.map { it[IS_SERVICE_RUNNING] }
    val accompanyIdFlow: Flow<Long?> = dataStore.data.map { it[ACCOMPANY_ID] }
}
