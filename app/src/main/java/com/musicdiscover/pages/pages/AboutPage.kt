package com.musicdiscover.pages.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.musicdiscover.components.Posts.PostsFeed
import com.musicdiscover.navigation.NavBarLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutPage(
    navController: NavHostController
) {
    val paragraph_spacing = 10.dp
    val paragraph_size = 17.sp

    NavBarLayout(navController) {
        Column (modifier = Modifier.padding(10.dp).fillMaxWidth()
            .fillMaxHeight()
//            .padding(0.dp, 70.dp, 0.dp, 0.dp)
            .verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.size(7.dp))
            Row (modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Center) { Text(text = "About", fontWeight = FontWeight.Bold, fontSize = 35.sp) }
            Spacer(modifier = Modifier.size(20.dp))

            Row (modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Start) {
                Text(text = "" +
                        "Welcome to MusicDiscover, your ultimate destination for exploring new music and sharing your favorite tunes with the world. Developed by Konstantino Moka as part of a university project, MusicDiscover is more than just an app—it's a vibrant community of music enthusiasts coming together to discover, share, and connect through the universal language of music."
                        + "", fontSize = paragraph_size)
            }
            Spacer(modifier = Modifier.size(paragraph_spacing))

            Row (modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Start) {
                Text(text = "" +
                        "At MusicDiscover, we believe that music is meant to be shared and enjoyed by everyone. Whether you're a seasoned music aficionado or just starting your musical journey, our platform provides the perfect space to unearth hidden gems, rediscover old favorites, and connect with like-minded individuals who share your passion for music."
                        + "", fontSize = paragraph_size)
            }
            Spacer(modifier = Modifier.size(paragraph_spacing))

            Row (modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Start) {
                Text(text = "" +
                        "With MusicDiscover, you have the power to share your favorite songs with the world. Simply upload your favorite tracks, albums, or playlists, and watch as fellow music lovers discover and engage with your selections. Whether it's a classic rock anthem, a soulful jazz ballad, or the latest indie hit, there's something for everyone to enjoy on MusicDiscover."
                        + "", fontSize = paragraph_size)
            }
            Spacer(modifier = Modifier.size(paragraph_spacing))

            Row (modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Start) {
                Text(text = "" +
                        "But MusicDiscover isn't just about sharing—it's also about discovering. Our intuitive platform makes it easy to explore a vast library of user-curated content, allowing you to dive deep into genres, artists, and songs you may have never encountered before. Whether you're in the mood for something upbeat and energetic or mellow and introspective, MusicDiscover has you covered."
                        + "", fontSize = paragraph_size)
            }
            Spacer(modifier = Modifier.size(paragraph_spacing))

            Row (modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Start) {
//                        "In addition to discovering new music, MusicDiscover also fosters a sense of community and connection among its users. Engage with fellow music lovers through likes, comments, and shares, and join in on lively discussions about your favorite artists, albums, and songs. With MusicDiscover, you're never alone on your musical journey."
                Text(text = "" +
                        "In addition to discovering new music, MusicDiscover also fosters a sense of community and connection among its users. Engage with fellow music lovers through likes about your favorite artists, albums, and songs. With MusicDiscover, you're never alone on your musical journey."
                        + "", fontSize = paragraph_size)
            }
            Spacer(modifier = Modifier.size(paragraph_spacing))

            Row (modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Start) {
                Text(text = "" +
                        "So whether you're looking to expand your musical horizons, connect with fellow music enthusiasts, or simply share your favorite songs with the world, MusicDiscover is the ultimate destination for all things music. Join us today and let the music discovery begin!"
                        + "", fontSize = paragraph_size)
            }
            Spacer(modifier = Modifier.size(paragraph_spacing))
        }
    }
}