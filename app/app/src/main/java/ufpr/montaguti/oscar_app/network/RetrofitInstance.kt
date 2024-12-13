import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ufpr.montaguti.oscar_app.network.AuthService
import ufpr.montaguti.oscar_app.network.DirectorApiService
import ufpr.montaguti.oscar_app.network.MovieApiService
import ufpr.montaguti.oscar_app.network.VoteService

object RetrofitInstance {
    private val BASE_API = "https://917d-2804-214-91f0-3c92-59fa-72f5-59eb-67f.ngrok-free.app/"

    val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    val voteService: VoteService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VoteService::class.java)
    }

    private val BASE_URL = "http://wecodecorp.com.br/ufpr/"

    val api: MovieApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }

    val api_2: DirectorApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DirectorApiService::class.java)
    }
}