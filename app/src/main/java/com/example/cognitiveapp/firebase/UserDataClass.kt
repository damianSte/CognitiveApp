package com.example.cognitiveapp.firebase


/**
 * Class representing a user in the cognitive app.
 *
 * @property id The unique identifier for the user. Default is an empty string.
 * @property name The name of the user. Default is an empty string.
 * @property email The email address of the user. Default is an empty string.
 * @property registeredUser A flag indicating whether the user is registered. Default is false.
 */
class User( val id: String="",
            val name: String="",
            val email: String="",
            val registeredUser: Boolean = false,
)