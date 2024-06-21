package com.example.cognitiveapp.Register

data class RegisterCredentials(
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var password: String = "",
    var repeatPassword: String = "",
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()
    }
}