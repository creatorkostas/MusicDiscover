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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.musicdiscover.R
import com.musicdiscover.auth.data.db.AddNewUser
import com.musicdiscover.auth.models.AuthViewModel
import com.musicdiscover.navigation.Screens
import com.musicdiscover.components.PasswordTextFieldWithIcon
import com.musicdiscover.components.TextFieldWithIcon
import com.musicdiscover.navigation.navigate

@Composable
fun RegisterPage(navController: NavHostController, context: Context) {
    val username = remember { AuthViewModel() }
    val password = remember { AuthViewModel() }
    val ret_password = remember { AuthViewModel() }
    val email = remember { AuthViewModel() }
    val first_name = remember { AuthViewModel() }
    val last_name = remember { AuthViewModel() }
    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(imageVector = Icons.Filled.AccountCircle, contentDescription = "", modifier = Modifier.size(80.dp))
            TextFieldWithIcon(username, fieldIcon = Icons.Filled.AccountCircle, fieldText1 = stringResource(R.string.username), fieldText2 = stringResource(R.string.username), Regex( "^(?!.*\\.\\.)(?!.*\\.\$)[^\\W]{5,30}\$"))
            Spacer(modifier = Modifier.height(10.dp))
            TextFieldWithIcon(email, fieldIcon = Icons.Filled.Email, fieldText1 = stringResource(R.string.email), fieldText2 = stringResource(R.string.email), Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))
            Spacer(modifier = Modifier.height(10.dp))
            TextFieldWithIcon(first_name, fieldIcon = null, fieldText1 = stringResource(R.string.firstName), fieldText2 = stringResource(R.string.firstName))
            Spacer(modifier = Modifier.height(10.dp))
            TextFieldWithIcon(last_name, fieldIcon = null, fieldText1 = stringResource(R.string.lastName), fieldText2 = stringResource(R.string.lastName))
            Spacer(modifier = Modifier.height(10.dp))
            PasswordTextFieldWithIcon(password, fieldIcon = Icons.Filled.Lock, fieldText1 = stringResource(R.string.password), fieldText2 = stringResource(R.string.password))
            Spacer(modifier = Modifier.height(10.dp))
            PasswordTextFieldWithIcon(ret_password, fieldIcon = Icons.Filled.Lock, fieldText1 = stringResource(R.string.password), fieldText2 = stringResource(R.string.password))
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                if(AddNewUser(context, username.text, email.text, password.text, ret_password.text, first_name.text, last_name.text).addUser()){
                    com.musicdiscover.components.Toast().makeAndShow(
                        context, R.string.register_success, Toast.LENGTH_SHORT
                    )
                    navigate(navController, Screens.Login.route, null)
                }else{
                    com.musicdiscover.components.Toast().makeAndShow(
                        context, R.string.error_try_again, Toast.LENGTH_SHORT
                    )
                }
            }) {Text(text = stringResource(R.string.register))}
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                navigate(navController, Screens.Login.route, null)
            }) {Text(text = stringResource(R.string.login))}

        }
    }
}