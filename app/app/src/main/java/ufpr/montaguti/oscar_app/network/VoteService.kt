package ufpr.montaguti.oscar_app.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Header
import ufpr.montaguti.oscar_app.model.Vote

interface VoteService {
    @POST("votes.json")
    fun create(
        @Header("X-User-Token") userToken: String,
        @Header("X-User-Email") userEmail: String,
        @Body request: Vote
    ): Call<Vote>
}