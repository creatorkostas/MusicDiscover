package com.musicdiscover.Data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.graphics.vector.ImageVector

data class Song(
    var name: String,
    var artist: String,
    var fb_song_id: String? = null,
    var image: String? = null,
)
