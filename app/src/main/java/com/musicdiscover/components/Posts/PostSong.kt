package com.musicdiscover.components.Posts

import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import com.musicdiscover.Data.Posts.MakePost
import com.musicdiscover.Data.Notifications.CustomNotifications
import com.musicdiscover.R
import com.musicdiscover.navigation.Screens
import com.musicdiscover.navigation.getCurrentPage
import com.musicdiscover.navigation.navigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//@Composable
//fun PostSong(songInfo: Song){
//    Box {
//        Row (modifier = Modifier.fillMaxWidth()) {
////            Image(imageVector = songInfo.image, contentDescription = "Song Image")
//        }
//        Row (modifier = Modifier.fillMaxWidth()) {
//            Text(text = songInfo.name)
//        }
//
//    }
//}


@Composable
fun MakePostFloatingButton(navController: NavController,screen: String){
    val openDialog = remember { mutableStateOf(false) }
    return FloatingActionButton(
        onClick = {
            openDialog.value = true
        },
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add",
            modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
        )
        NewPostDialog(openDialog, navController, screen)
    }
}

@Composable
fun NewPostDialog(openDialog: MutableState<Boolean>, navController: NavController, screen: String) {
    val context  = LocalContext.current
    var song_name = remember { mutableStateOf("") }
    
    if (openDialog.value) {
        AlertDialog(
            modifier = Modifier,
            onDismissRequest = { song_name.value = "" ; openDialog.value = false },
            title = {
                Text(text = "Post a new song!")
            },
            text = {
                OutlinedTextField(
                    value = song_name.value,
                    onValueChange = {
                        song_name.value = it
                    },
                    label = { Text(text = stringResource(R.string.post_song_name_field)) },
                    placeholder = { Text(text = stringResource(R.string.post_song_name_field)) },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
//                        CoroutineScope(Dispatchers.IO).launch{
                            if (song_name.value == "") {
                                com.musicdiscover.components.Toast().makeAndShow(context, R.string.empty_song_name, Toast.LENGTH_SHORT)
                            }
                            else if (MakePost(song_name.value, context).post()) {
                                com.musicdiscover.components.Toast().makeAndShow(context, R.string.song_posted_successfully, Toast.LENGTH_SHORT)
//                                navigate(navController, screen, null)
                            }
                            else {
                                com.musicdiscover.components.Toast()
                                    .makeAndShow(context, R.string.error_try_again, Toast.LENGTH_SHORT)
                                CustomNotifications(context).showBasicNotification("Main","Post song", "Something went wrong! Please try again" , NotificationCompat.PRIORITY_DEFAULT)
                            }

                            navigate(navController, screen, null)
                            openDialog.value = false
//                        }


                    }
                ) {
                    Text(stringResource(R.string.post))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        song_name.value = ""
                        openDialog.value = false
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            },
            shape = RoundedCornerShape(12.dp),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            iconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            textContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            tonalElevation = 1.dp
        )
    }
}