package com.example.swifty_companion.models

data class Versions (
    val large: String,
    val medium: String,
    val small: String,
    val micro: String
)

data class UserImage(
    val link: String,
    val versions: Versions
)

data class UserSearchModel(
    val login: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val url: String,
    val displayName: String,
    val image: UserImage
)
