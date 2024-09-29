package com.musicdiscover.pages.pages

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.musicdiscover.Data.Posts.Post
import com.musicdiscover.Data.ViewModels.ProfilePageViewModel
import com.musicdiscover.Data.ViewModels.UserViewModel
import com.musicdiscover.R
import com.musicdiscover.auth.data.Logout
import com.musicdiscover.components.Posts.MakePostFloatingButton
import com.musicdiscover.components.Posts.PostCard
import com.musicdiscover.components.Posts.PostsFeed
import com.musicdiscover.components.Toast
import com.musicdiscover.navigation.NavBarLayout
import com.musicdiscover.navigation.Screens
import com.musicdiscover.navigation.navigate


//@Suppress("PreviewAnnotationInFunctionWithParameters")
//@Preview(
//    showBackground = true,
//    showSystemUi = true, device = "spec:width=1080px,height=2400px",
//    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_UNDEFINED,
//    wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE
//)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    navController: NavHostController,
    context: Context
) {
//    CustomNotifications(LocalContext.current).createNotificationChannel()

//    CustomNotifications(LocalContext.current).showBasicNotification("test","test2","test2", NotificationCompat.PRIORITY_DEFAULT)
    val userViewModel: UserViewModel = viewModel(factory = UserViewModel.factory)
    userViewModel.setup(context)
    val profile_page_viewModel: ProfilePageViewModel = viewModel(factory = ProfilePageViewModel.factory)
    val local_songs by profile_page_viewModel.updatedDisplayedUserPosts(context).collectAsState(emptyList())
    val liked_posts by profile_page_viewModel.updatedDisplayedLikedPosts(context).collectAsState(emptyList())

    NavBarLayout(navController, { MakePostFloatingButton(navController, Screens.Profile.route) }) {

        when (LocalConfiguration.current.orientation){
            Configuration.ORIENTATION_LANDSCAPE -> {
                Row {
                    userCard(navController, context, userViewModel, local_songs.size.toString(),
                        Modifier
                            .padding(10.dp)
                            .fillMaxWidth(0.45f)
                            .fillMaxHeight())
                    Spacer(modifier = Modifier.height(1.dp))
                    tabs(local_songs = local_songs, liked_posts = liked_posts, navController)
                }
            }
            else -> {
                Column {
                    userCard(navController, context, userViewModel, local_songs.size.toString(),
                        Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .fillMaxHeight(0.25f))
                    Spacer(modifier = Modifier.height(5.dp))
                    tabs(local_songs = local_songs, liked_posts = liked_posts, navController)
                }
            }
        }
    }
}

@Composable
fun userCard(
    navController: NavHostController,
    context: Context,
    userViewModel: UserViewModel,
    user_posts: String,
    modifier: Modifier = Modifier
){
    OutlinedCard(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground)
    ) {
        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                userImage(navController)
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = userViewModel.name.value,
                    fontWeight = FontWeight.Bold
                )

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                userInfoRow(user_posts)
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    if (Logout(context).logout()) navigate(
                        navController,
                        Screens.Auth.route,
                        Screens.Main.route
                    )
                    else Toast().makeAndShow(
                        context,
                        R.string.logout_error,
                        android.widget.Toast.LENGTH_SHORT
                    )
                }) {
                    Text(text = "Logout")
                }
            }
//                        userInfoRow(userViewModel.user.value?.post_num.toString())

        }
    }
}

@Composable
fun tabs(
    local_songs: List<Post>,
    liked_posts: List<Post>,
    navController: NavController,
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Your Posts", "Liked")


    Column {


        TabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier,
                    selected = state == index,
                    onClick = { state = index },
                    text = {
                        Text(
                            text = title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = when (index) {
                                0 -> Icons.Default.Person
                                1 -> Icons.Default.Favorite
                                else -> Icons.Default.Favorite
                            },
                            contentDescription = null
                        )
                    },
                    enabled = true,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
        when (state) {
            0 -> {
                PostsFeed {

                    if (local_songs.isNotEmpty()) {
                        local_songs.forEach {

                            if (liked_posts.contains(it)) PostCard(navController , it, true)
                            else PostCard(navController, it, false)
                        }
                    } else {
                        Text(text = "You have not posted a song yet!")
                    }
                }
            }

            1 -> {
                PostsFeed {
                    if (liked_posts.isNotEmpty()) {
                        liked_posts.forEach { PostCard(navController, it, true) }
                    } else {
                        Text(text = "You have not liked a song yet!")
                    }
                }
            }
        }
    }
}

@Composable
fun userImage(navController: NavHostController) {
    Box(
        modifier = Modifier
            .size(120.dp)
//                            .background(color = Color.Blue)
            .padding(10.dp, 10.dp)
    ) {
        Image(
            imageVector = Icons.Rounded.AccountCircle,
            colorFilter = ColorFilter.lighting(
                MaterialTheme.colorScheme.onBackground,
                MaterialTheme.colorScheme.onBackground
            ),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize()
        )
        IconButton(
            onClick = { navigate(navController, Screens.EditUserInfo.route, null) },
            modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize(0.4f)
                    .align(Alignment.BottomEnd)
        ) {
            Image(
                imageVector = Icons.Filled.Create,
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.lighting(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.background
                ),
                contentDescription = "",
                modifier = Modifier
//                    .clip(CircleShape)
//                    .fillMaxSize(0.35f)
//                    .align(Alignment.BottomEnd)
//                                        .padding(10.dp)
                    .background(
                        MaterialTheme.colorScheme.onBackground,
                        CircleShape
                    ).size(35.dp)
//                                        .border(BorderStroke(2.dp, Color.Black), CircleShape)
            )
        }

    }
}

@Composable
fun userInfoRow(user_posts: String) {
    Row {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight(0.7f)
        ) {
            Text(text = "Posts")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = user_posts)
        }
        VerticalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight(0.6f)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight(0.7f)
        ) {
            Text(text = "Following")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Coming soon")
//                                    Text(text = userViewModel._user.value.users_following.toString())
        }
    }
}