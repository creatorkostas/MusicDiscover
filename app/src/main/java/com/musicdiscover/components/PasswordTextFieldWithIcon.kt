package com.musicdiscover.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.musicdiscover.auth.data.validators.UserValidator
import com.musicdiscover.auth.models.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldWithIcon(
    value: AuthViewModel = remember { AuthViewModel() },
    fieldIcon: ImageVector,
    fieldText1: String,
    fieldText2: String,

){
    val error = remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value.text,
        leadingIcon = { Icon(imageVector = fieldIcon, contentDescription = "") },
        //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
        onValueChange = {
            value.text = it
            error.value = UserValidator().isValid(it, Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}\$"))
        },
        label = { Text(text = fieldText1) },
        placeholder = { Text(text = fieldText2) },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = error.value

    )
}