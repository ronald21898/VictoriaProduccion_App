package com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class LoginViewModel : ViewModel() {

    // Simulación de usuarios obtenidos de una base de datos
    private val _users = MutableLiveData<List<String>>(listOf("Usuario1", "Usuario2", "Usuario3", "Usuario4"))
    val users: LiveData<List<String>> = _users

    private val _selectedUser = MutableLiveData<String>()
    val selectedUser: LiveData<String> = _selectedUser

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onUserSelected(user: String) {
        _selectedUser.value = user
        _loginEnable.value = user.isNotEmpty()


    }
    fun deleteUser(user: String) {
        _users.value = _users.value?.filter { it != user } // Elimina el usuario de la lista
        if (_selectedUser.value == user) {
            _selectedUser.value = "" // Deselecciona al usuario si se eliminó
            _loginEnable.value = false // Deshabilita el botón de inicio
        }
    }
    fun addUser(user: String) {
        _users.value = _users.value?.plus(user) // Agrega el usuario a la lista
    }

    suspend fun onLoginSelected() {
        _isLoading.value = true
        delay(2000)  // Simula el proceso de autenticación
        _isLoading.value = false
    }
}
