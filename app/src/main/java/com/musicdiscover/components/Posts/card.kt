package com.musicdiscover.components.Posts

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.musicdiscover.Data.Databases.Database.LocalDatabase
import com.musicdiscover.Data.Databases.Database.TABLEs.LikedPosts
import com.musicdiscover.Data.Posts.Post
import com.musicdiscover.navigation.Screens
import com.musicdiscover.navigation.navigate

@Composable
fun PostCard(
    navController: NavController,
    post_info: Post,
    isLiked: Boolean = false,
    context: Context = LocalContext.current,
    PostCardElevation: Dp = 4.dp,
    PostCardContentElevation: Dp = 4.dp,
    RowPadidng: Dp = 16.dp,
    CornersRadius: Dp = 16.dp,
    MainCardModifier: Modifier = Modifier,
    ContentCardModifier: Modifier = Modifier,
    UserOnClick: () -> Unit = {},
    SongOnClick: () -> Unit = {}
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = PostCardElevation
        ),
        modifier = Modifier
            //.height(100.dp)
            .fillMaxWidth(0.85f)
            .padding(0.dp, 10.dp)
            .then(MainCardModifier),
        shape = RoundedCornerShape(CornersRadius)
    ) {
        Column (modifier = Modifier.padding(10.dp)) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = UserOnClick),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = post_info.user.image,
                    contentDescription = "User Image",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(35.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(text = post_info.user.name, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.size(20.dp))

//        Row (modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                when (LocalConfiguration.current.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        Row {
                            Text(text = post_info.song.name, fontWeight = FontWeight.Bold)
                            Text(text = " by ")
                            Text(text = post_info.song.artist, fontWeight = FontWeight.Bold)
                        }
                    }

                    else -> {
                        Row {
                            Text(text = post_info.song.name, fontWeight = FontWeight.Bold)
                            Text(text = " by ")
                        }
                        Text(text = post_info.song.artist, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.size(15.dp))
                if (post_info.song.image != null){
                    AsyncImage(
                        model = post_info.song.image,
                        contentDescription = null,
                        modifier = Modifier.clip(Shapes().medium)
                    )
                }

            }
//        }
            Spacer(Modifier.size(20.dp))

            Row (modifier = Modifier.fillMaxWidth() ,horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                if (post_info.isUserPost) {
                    IconButton(
                        onClick = {
                            post_info.deletePost(context)
                            navigate(navController, Screens.Profile.route, null)
                        },
//                        modifier = Modifier.fillMaxWidth(0.2f),
                        enabled = true,
                        colors = IconButtonDefaults.iconButtonColors()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Post"
                        )
                    }
                    LikeButton(post_info, isLiked, Modifier.fillMaxWidth())
                }else{
                    LikeButton(post_info, isLiked, Modifier.fillMaxWidth())
                }
            }
//
//        Column(
//
//            modifier = Modifier
//                .padding(RowPadidng)
//                .fillMaxWidth()
//        ) {
//
//
//            Spacer(Modifier.size(RowPadidng))
//            Column(
//                //elevation = CardDefaults.cardElevation(defaultElevation = PostCardContentElevation),
//                modifier = Modifier
//                    .fillMaxWidth(1f)
//                    .clickable(onClick = SongOnClick)
//                    .then(ContentCardModifier),
////                shape = RoundedCornerShape(CornersRadius)
//            ) {
//
//                    Column (verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {  }
//                }
//                /*Row (modifier = Modifier.fillMaxWidth()) {
//                    androidx.compose.foundation.Image(
//                        modifier = Modifier.fillMaxWidth(),
//                        imageVector = songInfo.image,
//                        contentDescription = "Song Image",
//                        contentScale = ContentScale.Crop
//                    )
//                }*/
//            }
//        }
        }
    }
}


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun LikeButton(post_info: Post, isLiked: Boolean = false, modifier: Modifier = Modifier) {
    val checkedState = remember { mutableStateOf(isLiked) }
    val context = LocalContext.current
    IconToggleButton(
        modifier = modifier,
        checked = checkedState.value,
        onCheckedChange = {
//            CoroutineScope(Dispatchers.IO).launch{
            checkedState.value = !checkedState.value
            val liked_post = LikedPosts(post_info.post_fb_id)
            println("print: $liked_post")
            if (checkedState.value) {
            println("print: add")
                LocalDatabase.getDatabase(context).likedPostsDao().insert(liked_post)

            }
            else {
            println("print: delete")
//                        LocalDatabase.getDatabase(context).likedPostsDao().deleteAll()
                LocalDatabase.getDatabase(context).likedPostsDao().delete(liked_post)
//                    LocalDatabase.getDatabase(context).likedPostsDao().findAndDelete(user.fb_info_id,song.fb_song_id!!)

            }
//                    else LocalDatabase.getDatabase(context).likedPostsDao().findAndDelete(user.fb_info_id,song.fb_song_id!!)
            //            }

        },
//        modifier = Modifier.padding(10.dp)
    ) {
        val transition = updateTransition(checkedState.value, label = "")

//        val tint = MaterialTheme.colorScheme.error
        val tint = MaterialTheme.colorScheme.secondary
//        val tint = MaterialTheme.colorScheme.tertiary
//        val tint by transition.animateColor(label = "iconColor") { isChecked ->
//            if (isChecked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
//        }

        val size by transition.animateDp(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    keyframes {
                        durationMillis = 250
                        30.dp at 0 using LinearOutSlowInEasing
                        35.dp at 15 using FastOutLinearInEasing
                        40.dp at 75
                        35.dp at 150
                    }
                } else {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
            },
            label = "Size"
        ) { 30.dp }

        Icon(
            imageVector = if (checkedState.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = "Icon",
            tint = tint,
            modifier = Modifier.size(size)
        )
    }

}