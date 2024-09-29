package com.musicdiscover.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.musicdiscover.auth.data.validators.UserValidator
import com.musicdiscover.auth.models.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithIcon(
    value: AuthViewModel = remember { AuthViewModel() },
    fieldIcon: ImageVector?,
    fieldText1: String,
    fieldText2: String,
    regex: Regex? = null
){
    val error = remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value.text,
        leadingIcon = {
            if (fieldIcon != null) {
                Icon(imageVector = fieldIcon, contentDescription = "")
            }
        },
        onValueChange = {
            value.text = it
            error.value = if(regex != null) UserValidator().isValid(it, regex) else false
        },
        label = { Text(text = fieldText1) },
        placeholder = { Text(text = fieldText2) },
        singleLine = true,
        isError = error.value
    )
}