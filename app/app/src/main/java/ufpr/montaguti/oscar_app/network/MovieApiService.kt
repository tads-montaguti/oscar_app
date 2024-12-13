package ufpr.montaguti.oscar_app.network

import retrofit2.Call
import retrofit2.http.GET
import ufpr.montaguti.oscar_app.model.Movie

interface MovieApiService {
    @GET("filme") // Endpoint relativo à URL base
    fun getMovies(): Call<List<Movie>>
}