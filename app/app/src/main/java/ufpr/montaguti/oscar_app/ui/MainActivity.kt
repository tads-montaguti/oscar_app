package ufpr.montaguti.oscar_app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import ufpr.montaguti.oscar_app.R
import ufpr.montaguti.oscar_app.model.LoginRequest
import ufpr.montaguti.oscar_app.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var buttonLogin: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextSenha: EditText
    private var userPrefsName: String = "LoggedUser"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = getSharedPreferences(userPrefsName, Context.MODE_PRIVATE)
        val authToken = sharedPrefs.getString("LOGGED_USER_AUTH_TOKEN", null)

        if (authToken != null) loggedIn("Bem-vindo de volta")

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

                            loggedIn("Bem-vindo ${user.name}")
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

    private fun loggedIn(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
    }
}
