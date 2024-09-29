package com.musicdiscover.auth.data.validators

import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils

class UserValidator {

    fun isValid(value: String, regex: Regex): Boolean {
        return !(regex.containsMatchIn(value))
    }

    private fun checkUsername(username: String): Boolean {
        val is_in_db: Boolean = FirebaseUtils().searchAndGetDocument("users_info", "username", username) != null
        return !is_in_db && isValid(username, Regex( "^(?!.*\\.\\.)(?!.*\\.\$)[^\\W]{5,30}\$"))
    }

    private fun checkEmail(email: String): Boolean {
        val is_in_db: Boolean = FirebaseUtils().searchAndGetDocument("users", "email", email) != null
        return !is_in_db && isValid(email, Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))
    }

    fun checkUser(username: String, email: String): Boolean {
        return checkUsername(username) && checkEmail(email)
    }

//    fun checkAndReturn(username: String): String? {
//        return if (!isInDatabase(username) && !isValid(username)) username
//        else null
//    }
}