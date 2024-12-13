package ufpr.montaguti.oscar_app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import ufpr.montaguti.oscar_app.R
import ufpr.montaguti.oscar_app.model.LoginRequest
import ufpr.montaguti.oscar_app.model.MovieWithDirector
import ufpr.montaguti.oscar_app.model.User
import ufpr.montaguti.oscar_app.utils.VoteManager

class MainActivity : AppCompatActivity() {

    private lateinit var buttonLogin: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextSenha: EditText
    private val userPrefsName: String = "LoggedUser"
    private var votePrefsName: String = "VoteSubmited"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = getSharedPreferences(userPrefsName, Context.MODE_PRIVATE)
        val authToken = sharedPrefs.getString("LOGGED_USER_AUTH_TOKEN", null)

        if (authToken != null) {
            restoreVoteManagerFromPreferences()
            loggedIn("Bem-vindo de volta")
        }

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextSenha = findViewById(R.id.editTextSenha)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val senha = editTextSenha.text.toString().trim()

            val request = LoginRequest(email, senha)
            RetrofitInstance.authService.login(request).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()

                        if (user != null) {
                            val sharedPrefs = getSharedPreferences(userPrefsName, Context.MODE_PRIVATE)
                            val editor = sharedPrefs.edit()
                            editor.putString("LOGGED_USER_EMAIL", user.email)
                            editor.putString("LOGGED_USER_AUTH_TOKEN", user.authentication_token)
                            editor.putInt("LOGGED_USER_VOTING_TOKEN", user.voting_token)
                            editor.apply()


                            if (user.vote != null) {
                                updateVoteManagerFromPayload(user.vote.payload)
                                loggedIn("Bem-vindo ${user.name}, você já votou!")
                            } else {
                                loggedIn("Bem-vindo ${user.name}")
                            }
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Não foi possível se conectar ao servidor", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun updateVoteManagerFromPayload(payload: String) {
        try {
            val gson = Gson()
            val movieWithDirector = gson.fromJson(payload, MovieWithDirector::class.java)

            VoteManager.selectedMovie = movieWithDirector.movie
            VoteManager.selectedDirector = movieWithDirector.director
            VoteManager.isVoteSubmitted = true
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao processar o voto salvo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loggedIn(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
    }

    private fun restoreVoteManagerFromPreferences() {
        val prefsVote = getSharedPreferences(votePrefsName, Context.MODE_PRIVATE)
        val payload = prefsVote.getString("VOTE", null)
        if (payload != null) {
            updateVoteManagerFromPayload(payload)
        }
    }
}