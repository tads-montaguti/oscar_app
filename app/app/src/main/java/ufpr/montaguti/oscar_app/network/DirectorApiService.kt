package ufpr.montaguti.oscar_app.network

import retrofit2.Call
import retrofit2.http.GET
import ufpr.montaguti.oscar_app.model.Director

interface DirectorApiService {
    @GET("diretor")
    fun getDirectors(): Call<List<Director>>
}