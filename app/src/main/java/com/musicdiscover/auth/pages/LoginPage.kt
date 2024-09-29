package com.musicdiscover.auth.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.Data.Notifications.CustomNotifications
import com.musicdiscover.R
import com.musicdiscover.auth.data.validators.LoginValidator
import com.musicdiscover.auth.models.AuthViewModel
import com.musicdiscover.navigation.Screens
import com.musicdiscover.components.PasswordTextFieldWithIcon
import com.musicdiscover.components.TextFieldWithIcon
import com.musicdiscover.navigation.navigate
import kotlinx.coroutines.runBlocking

@Composable
fun LoginPage(nav_it: NavBackStackEntry, navController: NavHostController, context: Context) {

    var email = remember { AuthViewModel() }
    var password = remember { AuthViewModel() }
    var token = remember { AuthViewModel() }
    runBlocking {

//        DataStoreUtil(context).saveIn_User_DataStore(UserFields.USER_AUTH_TOKEN, "")
//        println("print: "+DataStoreUtil(context).readFrom_User_DataStore<String>(UserFields.USER_AUTH_TOKEN).toString())
//        try {
            token.text = DataStoreUtil(context).readFrom_User_DataStore<String?>(UserFields.USER_AUTH_TOKEN) ?: ""
//        } catch ( ex: Exception ){
//            token.text == null
//            println("print: "+ex.stackTrace.toString())
//        }
    }

    if (token.text != ""){
        navigate(navController, Screens.Main.route, Screens.Auth.route)
    }

    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(imageVector = Icons.Filled.AccountCircle, contentDescription = "", modifier = Modifier.size(80.dp))
            TextFieldWithIcon(value = email, fieldIcon = Icons.Filled.Email, fieldText1 = stringResource(R.string.email), fieldText2 = stringResource(R.string.email), Regex("^[\\w-]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))
            Spacer(modifier = Modifier.height(10.dp))
            PasswordTextFieldWithIcon(value = password, fieldIcon = Icons.Filled.Lock, fieldText1 = stringResource(R.string.password), fieldText2 = stringResource(R.string.password))
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                val auth = LoginValidator().validCredentials(context, email.text, password.text, token)
                if (auth != null) navigate(navController, Screens.Main.route, Screens.Auth.route)
                else{
                    CustomNotifications(context).showBasicNotification("Main","Unsuccessful login!", "Something went wrong! Please try again" , NotificationCompat.PRIORITY_DEFAULT)
                    com.musicdiscover.components.Toast().makeAndShow(context, R.string.wrong_email_or_password, Toast.LENGTH_SHORT)
                }
            }) { Text(text = stringResource(R.string.login)) }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                navigate(navController, Screens.Register.route, null)
            }) { Text(text = stringResource(R.string.register)) }

                }
        }

}