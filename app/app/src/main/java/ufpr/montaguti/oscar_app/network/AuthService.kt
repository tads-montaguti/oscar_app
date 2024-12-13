package ufpr.montaguti.oscar_app.network

import ufpr.montaguti.oscar_app.model.LoginRequest
import ufpr.montaguti.oscar_app.model.User

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface AuthService {
    @POST("auth/sign_in.json")
    fun login(@Body request: LoginRequest): Call<User>
}