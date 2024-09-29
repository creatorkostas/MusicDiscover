package com.musicdiscover.auth.data.validators

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils
import com.musicdiscover.auth.data.getRandomString
import com.musicdiscover.auth.models.AuthViewModel
import com.musicdiscover.navigation.Screens
import com.musicdiscover.navigation.navigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

//import kotlinx.coroutines.launch

class LoginValidator {

    fun validCredentials(context: Context, email: String, password: String, auth: AuthViewModel): String? {
        val db = FirebaseUtils()
        var valid = false
        auth.text = ""
        val time = Timestamp.now()

        val hashedPassword: String? = PasswordValidator().hashPassword(password)

        if (hashedPassword != null) {
            val user = db.getUser(email, hashedPassword)
            val user_info_ref = user?.let { db.getUserInfoDocument(it.reference) }
            if (user_info_ref != null){
                CoroutineScope(Dispatchers.IO).launch {
                    DataStoreUtil(context).saveIn_User_DataStore<String>(UserFields.USER_ID, user_info_ref.id)
                    DataStoreUtil(context).saveIn_User_DataStore<String>(UserFields.USER_NAME, user_info_ref.data?.get("username").toString())
//                }
//                CoroutineScope(Dispatchers.IO).launch {
                    val db_session = db.searchAndGetDocument<DocumentReference>("sessions", "user", user!!.reference)
                    if (db_session == null) {
                        auth.text = getRandomString(25)
                        DataStoreUtil(context).saveIn_User_DataStore<String>(UserFields.USER_AUTH_TOKEN, auth.text)
                        db.addSession(auth.text,user_info_ref.reference)
                    }else{ auth.text = db_session.data?.get("token").toString() }
                }
                valid = true
            }
        }

        return if (valid) auth.text
        else null
    }
}