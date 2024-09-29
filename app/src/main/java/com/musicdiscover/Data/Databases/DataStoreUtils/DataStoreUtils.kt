package com.musicdiscover.Data.Databases.DataStoreUtils

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import java.util.concurrent.Flow

enum class UserFields() {

   USER_NAME {
       override fun <T> getField(): Preferences.Key<T> = stringPreferencesKey("user_name") as Preferences.Key<T>
   },
   USER_ID {
       override fun <T> getField(): Preferences.Key<T> = stringPreferencesKey("user_id") as Preferences.Key<T>
   },
    USER_AUTH_TOKEN {
        override fun <T> getField(): Preferences.Key<T> = stringPreferencesKey("user_auth_token") as Preferences.Key<T>
    }

   ;

   abstract fun <T> getField(): Preferences.Key<T>

}

enum class SettingsFields() {
    THEME {
        override fun <T> getField(): Preferences.Key<T> = stringPreferencesKey("user_name") as Preferences.Key<T>
    };

    abstract fun <T> getField(): Preferences.Key<T>

}


class DataStoreUtil(private val context: Context) {
    // to make sure there's only one instance
    companion object {


        private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore("settings")
        private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore("user")
    }

    suspend fun <T> readFrom_Settings_DataStore(field : SettingsFields): T? {
        var value: T
        val data = context.settingsDataStore.data.map { preferences ->
            preferences[field.getField<T>()]
        }
        value = data.first() as T
        return value
    }

    suspend fun <T> saveIn_Settings_DataStore(field : SettingsFields, value: T) {
        context.settingsDataStore.edit { preferences ->
            preferences[field.getField<T>()] = value
        }
    }

    suspend fun <T> readFrom_User_DataStore(field : UserFields): T {
        var value: T
        val data = context.userDataStore.data.map { preferences ->
            preferences[field.getField<T>()]
        }

        value = data.first() as T
        return value
    }

    suspend fun <T> saveIn_User_DataStore(field : UserFields, value: T) {
        context.userDataStore.edit { preferences ->
            preferences[field.getField<T>()] = value
        }
    }

}