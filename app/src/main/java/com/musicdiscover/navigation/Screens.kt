package com.musicdiscover.navigation

sealed class Screens(val route: String) {
    object Main : Screens(route = "main")
    object Home : Screens(route = "home")
    object Auth : Screens(route = "auth")
    object Login : Screens(route = "login")
    object Register : Screens(route = "register")
    object Profile : Screens(route = "profile")
    object Settings : Screens(route = "settings")
    object About : Screens(route = "about")
    object EditUserInfo : Screens(route = "edituserinfo")
    object Dev : Screens(route = "dev")
    object NotImplemented : Screens(route = "not_implemented")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg")}
        }
    }
}