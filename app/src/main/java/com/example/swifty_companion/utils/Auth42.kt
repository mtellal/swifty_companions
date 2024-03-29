package com.example.swifty_companion.utils

import com.example.swifty_companion.BuildConfig
import com.example.swifty_companion.models.CoalitionModel
import com.example.swifty_companion.models.UserDataModel
import com.example.swifty_companion.models.UserSearchModel
import com.google.gson.Gson
import com.google.gson.JsonParseException
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
    private val client_id = BuildConfig.CLIENT_UID;
    private val client_secret = BuildConfig.CLIENT_SECRET;
    private val grant_type = "client_credentials"

    var token: TokenResponse? = null
    private val client = OkHttpClient()

    fun fetchAccessToken(): String? {

        val formBody = FormBody.Builder()
            .add("grant_type", grant_type)
            .add("client_id", client_id)
            .add("client_secret", client_secret)
            .build()

        try {
            val request = Request.Builder()
                .url(authURL)
                .post(formBody)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected error $response")
                this.token = Gson().fromJson(response.body!!.string(), TokenResponse::class.java)
                println("token => $token")
            }
        } catch (e: IOException) {
            println("Error (IO): fetchAccessToken request => $e")
        } catch (e: Exception) {
            println("Error: fetchAccessToken request => $e")
        }
        return token?.access_token
    }

    fun findPeerRequest(login: String?): List<UserSearchModel>? {
        if (login == null) return (null)
        var users: List<UserSearchModel>? = null
        var url: String = "https://api.intra.42.fr/v2/users"
        if (login != null)
            url += "?range[login]=" + login.lowercase() + "," + login.lowercase() + "z"
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer ${token?.access_token}")
            .build()
        client.newCall(request).execute().use {
            if (!it.isSuccessful) throw IOException("Unexpected error $it")
            val itemType = object : TypeToken<List<UserSearchModel>>() {}.type
            users = Gson().fromJson<List<UserSearchModel>>(it.body!!.string(), itemType)

            return users
        }
    }

    fun userDataRequest(userId: Int?): UserDataModel? {
        if (userId == null) return (null)
        var url: String = "https://api.intra.42.fr/v2/users/$userId"
        var user: UserDataModel? = null
        if (userId == null) println("Invalid userId")
        try {
            val request = Request.Builder()
                .url(url)
                .header("Authorization", "Bearer ${token?.access_token}")
                .build()

            client.newCall(request).execute().use {
                if (!it.isSuccessful) throw IOException("user/$userId call failed")
                val itemType = object : TypeToken<UserDataModel>() {}.type
                user = Gson().fromJson(it.body!!.string(), itemType)
            }
        } catch (e: IOException) {
            println("Error (IO): userDataRequest request => $e")
        } catch (e: Exception) {
            println("Error: userDataRequest request => $e")
        }
        return user
    }

    fun userCoalition(username: String?): Array<CoalitionModel>? {
        if (username == null) return (null)
        var url: String = "https://api.intra.42.fr/v2/users/${username}/coalitions"
        var coalition: Array<CoalitionModel>? = null
        try {
            val request = Request.Builder()
                .url(url)
                .header("Authorization", "Bearer ${token?.access_token}")
                .build()

            client.newCall(request).execute().use {
                if (!it.isSuccessful) throw IOException("coalition/$username call failed")
                val itemType = object : TypeToken<Array<CoalitionModel>?>() {}.type
                coalition = Gson().fromJson(it.body!!.string(), itemType)
            }
        } catch (e: IOException) {
            println("Error (IO): userCoalition request => $e")
        } catch (e: Exception) {
            println("Error: userCoalition request => $e")
        }
        return coalition
    }

}