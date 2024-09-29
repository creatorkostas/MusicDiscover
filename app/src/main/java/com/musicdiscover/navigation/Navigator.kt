package com.musicdiscover.navigation

import android.content.Context
import android.content.res.Resources.Theme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.SettingsFields
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.auth.models.AuthViewModel
import com.musicdiscover.pages.Dev.DevPage
import com.musicdiscover.auth.pages.LoginPage
import com.musicdiscover.auth.pages.RegisterPage
import com.musicdiscover.pages.mainpage.MainPage
import com.musicdiscover.pages.pages.AboutPage
import com.musicdiscover.pages.pages.EditUserInfoPage
import com.musicdiscover.pages.pages.ProfilePage
import com.musicdiscover.pages.pages.SettingsPage
import com.musicdiscover.ui.theme.MusicDiscoverTheme
import kotlinx.coroutines.runBlocking


@Composable
fun themePage(context: Context, content: @Composable() () -> Unit){
    var theme: String? = getTheme(context)

    MusicDiscoverTheme(theme = theme) {
        Surface {
            content()
        }}
}

fun getTheme(context: Context):String? {
    var theme: String? = null
    runBlocking {
        val temp_theme = DataStoreUtil(context).readFrom_Settings_DataStore<String>(SettingsFields.THEME)
        if (temp_theme != null) {
            theme = temp_theme
        }
    }

    return theme
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigator(context: Context, theme: String? = null) {
    val navController = rememberNavController()
//    val theme
//    var theme: String? = null
//    runBlocking {
//        val temp_theme = DataStoreUtil(context).readFrom_Settings_DataStore<String>(SettingsFields.THEME)
//        if (temp_theme != null) {
//            theme = temp_theme
//        }
//    }
//
//    MusicDiscoverTheme(theme = theme) {
//        Surface {

    NavHost(navController = navController, startDestination = Screens.Auth.route) {

        navigation(startDestination = Screens.Login.route, route = Screens.Auth.route){

            composable(Screens.Login.route) {
                var token = ""
                runBlocking {
                    token = DataStoreUtil(context).readFrom_User_DataStore<String?>(UserFields.USER_AUTH_TOKEN) ?: ""
                }
                if (token != ""){
                    navigate(navController, Screens.Main.route, Screens.Auth.route)
                }else{
                    themePage(context){LoginPage(it, navController, context)}
                }
            }
            composable(Screens.Register.route) { themePage(context){RegisterPage(navController, context)} }
        }

        navigation(startDestination = Screens.Profile.route, route = Screens.Main.route){
            composable(
                route = Screens.Home.route,
            ) { _ ->

                themePage(context){MainPage(navController)}
            }
            composable(Screens.Profile.route){
                themePage(context){ProfilePage(navController, context)}
            }

            composable(Screens.Settings.route){
                themePage(context){SettingsPage(navController = navController, context = context, getTheme(context))}
            }

            composable(Screens.Dev.route){
                themePage(context){DevPage(navController = navController, context = context)}
            }

            composable(Screens.About.route){
                themePage(context){
                AboutPage(navController = navController)}
            }

            composable(Screens.EditUserInfo.route){
                themePage(context){
                EditUserInfoPage(navController = navController, context = context)}
            }

            composable(Screens.NotImplemented.route){
                themePage(context){
                NavBarLayout(navController = navController) {
                    Text(text = "Not implemented yet!!!")
                }}
            }
        }



    }


//        }
//    }
}

fun navigate(navController: NavController, to: String, remove: String? = null){
//    if (navController.currentDestination?.route == to) return
    navController.navigate(to) {
        if (remove != null) {
            popUpTo(remove) {
                inclusive = true
            }
        }
    }
    navController.currentDestination?.route
}



fun getCurrentPage(navController: NavController): String? {
    return navController.currentBackStackEntry?.destination?.route
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}