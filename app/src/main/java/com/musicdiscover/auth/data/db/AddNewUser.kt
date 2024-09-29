package com.musicdiscover.auth.data.db

import android.annotation.SuppressLint
import android.content.Context
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils
import com.musicdiscover.Data.devPrint
import com.musicdiscover.auth.data.validators.PasswordValidator
import com.musicdiscover.auth.data.validators.RegisterValidator
import com.musicdiscover.auth.models.AuthViewModel
import kotlinx.coroutines.runBlocking

class AddNewUser(
    context: Context,
    username: String,
    email: String,
    password: String,
    ret_password: String,
    first_name: String,
    last_name: String
)
{
//
    private val context: Context = context
    private val email: String = email
    private val ret_password: String = ret_password
    private val password: String = password
    private val username: String = username
    private val first_name: String = first_name
    private val last_name: String = last_name
    private var user_id: String? = null

    fun checkFields(): Boolean {
        return RegisterValidator()
            .isValidUser(
                this.username,
                this.password,
                this.ret_password,
                this.email
            ) && RegisterValidator().isValidName(this.first_name, this.last_name)
    }

    fun updateUser(): Boolean {
        runBlocking {
            user_id = DataStoreUtil(context).readFrom_User_DataStore<String>(UserFields.USER_ID)
//            deferredResult.await()
        }
//        var success: Boolean = false
        devPrint("not passed")
        devPrint("not passed "+ checkFields())
        if (checkFields() && user_id != null){
            println("passed")
            val db = FirebaseUtils()
            val UserInfoRef = db.getDocument("users_info", user_id!!)
            val UserRef = UserInfoRef?.data?.get("user") as DocumentReference
            if (UserRef != null) {
                val user: HashMap<String, Any> = hashMapOf(
                    "email" to this.email.toString(),
                    "password" to PasswordValidator().hashPassword(this.password).toString()
                )

                val user_info = hashMapOf(
                    "first_name" to this.first_name.toString(),
                    "last_name" to this.last_name.toString(),
                    "role" to "user",
                    "user" to UserRef,
                    "username" to this.username
                )

//            newUserRef.set(user).addOnSuccessListener{ newUserInfoRef.set(user_info).addOnSuccessListener{ success = true}.addOnFailureListener { e -> println("passed "+ e.message)} }
//                .addOnFailureListener { e -> println("passed "+ e.message)}

                db.runFirebaseTaskSync(UserRef.update(user), true)
                db.runFirebaseTaskSync((UserInfoRef as DocumentReference).update(user_info), true)

                return true
            }
            return false
        }
        return false
    }
    @SuppressLint("SuspiciousIndentation")
    fun addUser(): Boolean {
        var success: Boolean = false
        devPrint("not passed")
        devPrint("not passed "+ checkFields())
        if (checkFields()){
            println("passed")
            val db: FirebaseFirestore = FirebaseFirestore.getInstance()
            val newUserRef = db.collection("users").document()
            val newUserInfoRef = db.collection("users_info").document()
            val user = hashMapOf(
                "email" to "${this.email.toString()}",
                "password" to "${PasswordValidator().hashPassword(this.password).toString()}"
            )

            val user_info = hashMapOf(
                "first_name" to "${this.first_name.toString()}",
                "last_name" to "${this.last_name.toString()}",
                "role" to "user",
                "user" to newUserRef,
                "username" to "${this.username.toString()}"
            )

            newUserRef.set(user).addOnSuccessListener{ newUserInfoRef.set(user_info).addOnSuccessListener{ success = true}.addOnFailureListener { e -> println("passed "+ e.message)} }
                .addOnFailureListener { e -> println("passed "+ e.message)}
        }
        return true
    }
}