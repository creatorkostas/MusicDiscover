package com.musicdiscover.Data

fun devPrint(text: String){
    if (Global().dev_log) println("print: $text")
}