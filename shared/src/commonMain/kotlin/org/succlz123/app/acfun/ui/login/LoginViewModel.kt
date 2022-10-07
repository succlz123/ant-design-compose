package org.succlz123.app.acfun.ui.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.ui.focus.FocusRequester
import kotlinx.coroutines.flow.MutableStateFlow
import org.succlz123.lib.vm.BaseViewModel

class LoginViewModel : BaseViewModel() {

    companion object {
        val LOCAL_USER = mapOf("admin" to "admin")
    }

    val userName = MutableStateFlow("")

    val password = MutableStateFlow("")

    fun login(cb: (Boolean) -> Unit) {
        if (LOCAL_USER.get(userName.value) == password.value) {
            cb.invoke(true)
        } else {
            cb.invoke(false)
        }
    }
}
