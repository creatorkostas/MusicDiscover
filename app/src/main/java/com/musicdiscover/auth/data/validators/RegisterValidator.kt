package com.musicdiscover.auth.data.validators

class RegisterValidator() {
    private var good_user: Boolean = false
    private var good_password: Boolean = false

    fun isValidUser(username:String, password:String, ret_password:String, email:String): Boolean{
        return true
        var newpassword = PasswordValidator().checkAndEncrypt(password)
        var newretpassword = PasswordValidator().checkAndEncrypt(ret_password)
        good_user = UserValidator().checkUser(username, email)
        if (newpassword != null && newretpassword != null) good_password = (newpassword == newretpassword)
        return good_user && good_password
    }

    fun isValidName(first_name: String, last_name: String): Boolean {
        return true
    }
}