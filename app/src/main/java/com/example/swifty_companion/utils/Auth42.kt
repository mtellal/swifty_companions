package com.example.swifty_companion.utils

import com.example.swifty_companion.models.UserSearchModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: String,
)

class Auth {

    private val authURL = "https://api.intra.42.fr/oauth/token"
    private val client_id = ""
    private val client_secret = ""
    private val grant_type = "client_credentials"

    var token: TokenResponse? = null
    private val client = OkHttpClient()

    suspend fun httpRequest():String? {

        val formBody = FormBody.Builder()
            .add("grant_type", grant_type)
            .add("client_id", client_id)
            .add("client_secret", client_secret)
            .build()

        val request = Request.Builder()
            .url(authURL)
            .post(formBody)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected error $response")
                this.token = Gson().fromJson(response.body!!.string(), TokenResponse::class.java)
                println("token from httpRequest ${this.token}")
            }
        } catch (e: Exception) {
            println("Error: oauthToken request $e")
        }
        return token?.access_token
    }

    suspend fun findPeerRequest(login: String?): List<UserSearchModel>? {
        var users : List<UserSearchModel>? = null
        var url: String = "https://api.intra.42.fr/v2/users"
        if (login != null)
            url += "?range[login]=" + login.lowercase() + "," + login.lowercase() + "z"
        println("request with url => $url")
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer ${token?.access_token}")
            .build()
        try {
            client.newCall(request).execute().use {
                if (!it.isSuccessful) throw IOException("Unexpected error $it")
                println("Users data fetched")
                val itemType = object : TypeToken<List<UserSearchModel>>() {}.type
                users = Gson().fromJson<List<UserSearchModel>>(it.body!!.string(), itemType)
                println("Users fetched")
            }
        }
        catch (e: Exception) {
            println("Error findPeerRequest $e")
        }
        return users
    }
}