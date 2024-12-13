package ufpr.montaguti.oscar_app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                        Toast.makeText(this@MainActivity, "Sucesso", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this@MainActivity, MainActivity::class.java))
//                        finish()
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
}
