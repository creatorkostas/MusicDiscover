package com.musicdiscover.components.Posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PostsFeed(
//    post_cards:Po by { mutableListOf<Post>() }
    //modifier: Modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(0.dp, 10.dp, 0.dp, 0.dp)
    content: @Composable() () -> Unit
) {

    Column (
        verticalArrangement =  Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
//        contentAlignment = Alignment.TopCenter,

        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
//            .padding(0.dp, 70.dp, 0.dp, 0.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            content()

        }


    }
}