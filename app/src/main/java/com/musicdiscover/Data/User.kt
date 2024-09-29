package com.musicdiscover.Data

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

data class User(
    var name: String = "",
    var post_num: Int? = 0,
    var users_following: Int? = 0,
    var image: ImageVector = Icons.Filled.AccountCircle,
    var role: Role = Role(),
    var fb_info_id: String
)

sealed interface UserEvent{
    object getImage: UserEvent
    object getName: UserEvent
    data class setName(val token: String): UserEvent
    data class Setup(val name: String, val image: ImageVector?, val role: Role?): UserEvent
}



class UserViewModelOLD(): ViewModel() {

    var _name = MutableStateFlow("")
//    var _user = MutableStateFlow(User(name = _name.value, fb_info_id = get<String>(UserFields.USER_ID) ))
    var _user = MutableStateFlow<User?>(null)
    var _image = MutableStateFlow(ImageVector)
//    val state = combine(_state, _token) { state, token ->
//        state.copy (
//            token = token
//        )

//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SessionState())
    fun onEvent(event: UserEvent){
        when(event){
            is UserEvent.Setup -> {
                val name = event.name
                var image = event.image
                var role = event.role

                if(name.isBlank()) {
                    return
                }

                if(image == null){
                    image = Icons.Filled.AccountCircle
                }

                if (role == null){
                    role = Role()
                }

                _user.update {
                    it?.copy(
                        name, 0,0, image, role
                    )
                }

                _name.value = name
//                _image.value = image

            }
            UserEvent.getImage -> TODO()
            UserEvent.getName -> {}
            is UserEvent.setName -> TODO()
        }
    }
}