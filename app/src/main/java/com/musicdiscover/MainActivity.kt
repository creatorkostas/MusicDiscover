package com.musicdiscover

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.rxjava3.RxDataStoreBuilder
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteQuery
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.SettingsFields
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.Data.Databases.Database.LocalDatabase
import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils
import com.musicdiscover.Data.Notifications.ChannelInfo
import com.musicdiscover.Data.Notifications.CustomNotifications

import com.musicdiscover.navigation.Navigator
import com.musicdiscover.ui.theme.MusicDiscoverTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        analytics = Firebase.analytics
        val db = FirebaseUtils()
        val settings = firestoreSettings {
            // Use persistent disk cache (default)
            println("print: Persistent Cache is enabled")
            setLocalCacheSettings(persistentCacheSettings {})
        }
        db.db.firestoreSettings = settings

        Firebase.firestore.persistentCacheIndexManager?.apply {
            // Indexing is disabled by default
            println("print: Indexing is enabled")
            enableIndexAutoCreation()
        } ?: println("print: indexManager is null")

        val notifications_channels = mutableListOf<ChannelInfo>()
        notifications_channels.add(ChannelInfo("Main","MusicDiscover main notification channel", NotificationManager.IMPORTANCE_DEFAULT))
        CustomNotifications(applicationContext).createNotificationChannels(notifications_channels.toList())

        var theme: String? = null
        runBlocking {
            val temp_theme = DataStoreUtil(applicationContext).readFrom_Settings_DataStore<String>(SettingsFields.THEME)
            if (temp_theme != null) {
                theme = temp_theme
            }
        }

        setContent {
            println("")

//            MusicDiscoverTheme(theme = theme) {
//                Surface {
                    Navigator(applicationContext, theme)
                }
//            }
//        }
    }
}