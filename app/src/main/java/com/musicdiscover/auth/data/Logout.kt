package com.musicdiscover.auth.data

import android.content.Context
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.Data.Databases.Database.LocalDatabase
import com.musicdiscover.Data.Databases.Database.TABLEs.local_session
import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils
import kotlinx.coroutines.runBlocking

class Logout(val context: Context) {
    val db = FirebaseUtils()
    val local_db = DataStoreUtil(context)
    lateinit var token: String

    fun logout(): Boolean{
        return deleteLocalSession() && deleteFirebaseSession()
    }
    private fun deleteLocalSession(): Boolean{
        runBlocking{
            token = local_db.readFrom_User_DataStore(UserFields.USER_AUTH_TOKEN)
            local_db.saveIn_User_DataStore(UserFields.USER_ID,"")
            local_db.saveIn_User_DataStore(UserFields.USER_AUTH_TOKEN,"")
            local_db.saveIn_User_DataStore(UserFields.USER_NAME,"")
        }
        LocalDatabase.getDatabase(context).sessionDao().delete(localSession = local_session(token))

        return true
    }

    private fun deleteFirebaseSession(): Boolean{
        db.deleteDocument(
            "sessions",
            db.searchAndGetDocument("sessions", "Token", token)?.id ?: ""
        )
        return true
    }
}