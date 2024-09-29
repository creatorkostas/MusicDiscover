package com.musicdiscover.Data

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.room.Room

data class Global(
    val dev_log: Boolean = true
)