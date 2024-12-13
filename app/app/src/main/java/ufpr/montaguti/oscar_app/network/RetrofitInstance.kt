import android.content.Context
import android.content.SharedPreferences
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ufpr.montaguti.oscar_app.App
import ufpr.montaguti.oscar_app.network.AuthService

object RetrofitInstance {
    private const val PREFS_NAME = "AppPreferences"
    private const val BASE_URL_KEY = "BASE_URL"
    private const val DEFAULT_BASE_URL = "http://10.0.2.2:3000/"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun getBaseUrl(context: Context): String {
        val prefs = getSharedPreferences(context)
        return DEFAULT_BASE_URL
//        return prefs.getString(BASE_URL_KEY, DEFAULT_BASE_URL) ?: DEFAULT_BASE_URL
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(getBaseUrl(App.instance))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
}
