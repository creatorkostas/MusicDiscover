package com.musicdiscover.pages.pages

import android.content.Context
import android.content.res.Resources.Theme
import android.os.Build
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.SettingsFields
import com.musicdiscover.Data.devPrint
import com.musicdiscover.components.Posts.MakePostFloatingButton
import com.musicdiscover.components.Posts.PostsFeed
import com.musicdiscover.navigation.NavBarLayout
import com.musicdiscover.navigation.Screens
import com.musicdiscover.navigation.navigate
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    navController: NavHostController,
    context: Context,
    theme: String? = null
) {
    val checkedState = remember { mutableStateOf(false) }

    NavBarLayout(navController) {
        PostsFeed {
//            Row (verticalAlignment = Alignment.CenterVertically) {
//                Text(text = "Dynamic Colors")
//                Spacer(modifier = Modifier.width(10.dp))
//                Switch(
//                    checked = checkedState.value,
//                    onCheckedChange = {
//                        checkedState.value = !checkedState.value
//                    }
//                )
//            }
//                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Theme")
            Row {
                dropDown(navController,context, theme)
//                DropdownMenu(expanded = true, onDismissRequest = { /*TODO*/ }) {
//                    DropdownMenuItem(text = { Text(text = "text") }, onClick = { /*TODO*/ })
//                    DropdownMenuItem(text = { Text(text = "text") }, onClick = { /*TODO*/ })
//                    DropdownMenuItem(text = { Text(text = "text") }, onClick = { /*TODO*/ })
//                }
            }
        }
        
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun dropDown(navController: NavController, context: Context, theme: String? = null){
    val options: List<String>

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        options = listOf("Light", "Dark", "System", "Dynamic")
    }else{
        options = listOf("Light", "Dark", "System")
    }

//    LocalContext.current.theme.
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    if (theme != null) selectedOptionText = theme

    ExposedDropdownMenuBox(
        modifier = Modifier.clip(Shapes().medium),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
//            label = { Text("Theme") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            modifier = Modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    modifier = Modifier,
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        runBlocking {
                            DataStoreUtil(context).saveIn_Settings_DataStore<String>(SettingsFields.THEME, selectionOption)
                            devPrint(selectionOption)
                        }
                        navigate(navController, Screens.Settings.route, null)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}