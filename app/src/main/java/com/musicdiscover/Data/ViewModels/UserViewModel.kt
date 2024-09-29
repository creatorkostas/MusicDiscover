package com.musicdiscover.Data.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.SettingsFields
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils
import com.musicdiscover.Data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class UserViewModel : ViewModel() {
    lateinit var user: MutableStateFlow<User>
    lateinit var name: MutableStateFlow<String>
    lateinit var first_name: MutableStateFlow<String>
    lateinit var last_name: MutableStateFlow<String>
    lateinit var email: MutableStateFlow<String>
    lateinit var user_document: MutableStateFlow<DocumentSnapshot?>
    lateinit var user_info_document: MutableStateFlow<DocumentSnapshot?>
    lateinit var theme: MutableStateFlow<String>

    fun setup(context: Context){
        val db = DataStoreUtil(context)
        runBlocking {
            user = MutableStateFlow<User>(User(
                name = db.readFrom_User_DataStore<String>(UserFields.USER_NAME).toString(),
                fb_info_id =  db.readFrom_User_DataStore<String>(UserFields.USER_ID).toString()
            ))
        }
        name = MutableStateFlow<String>(user.value.name)
    }

    fun refreshTheme(context: Context){
        var t_theme: String? = null
        runBlocking {
            val temp_theme = DataStoreUtil(context).readFrom_Settings_DataStore<String>(
                SettingsFields.THEME)
            if (temp_theme != null) {
                t_theme = temp_theme
            }
        }

        theme = MutableStateFlow<String>(t_theme.toString())
    }

    fun getInfo(){
        user_info_document = MutableStateFlow<DocumentSnapshot?>(FirebaseUtils().getDocument("users_info", user.value.fb_info_id))
        first_name = MutableStateFlow<String>(user_info_document.value?.get("first_name").toString())
        last_name = MutableStateFlow<String>(user_info_document.value?.get("last_name").toString())
        user_document = MutableStateFlow<DocumentSnapshot?>(FirebaseUtils().getDocument("users", (user_info_document.value?.get("user") as DocumentReference).id))
        email = MutableStateFlow<String>(user_document.value?.get("email").toString())
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UserViewModel()
//                val application = (this[APPLICATION_KEY] as FlightResearchApplication)
//                HomeScreenViewModel(application.database.AirportDao())
            }
        }
    }
}