package ufpr.montaguti.oscar_app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ufpr.montaguti.oscar_app.R

class HomeActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editTextToken: EditText
    private lateinit var buttonVoteMovie: Button
    private lateinit var buttonVoteDirector: Button
    private lateinit var buttonConfirmVote: Button
    private lateinit var buttonLogout: Button

    private var userPrefsName: String = "LoggedUser"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val userPrefs = getSharedPreferences(userPrefsName, Context.MODE_PRIVATE)
        val token = userPrefs.getInt("LOGGED_USER_VOTING_TOKEN", -1);

        if (token < 0) {
            logout("Erro ao obter token de votação")
        }

        imageView = findViewById(R.id.imageView)
        editTextToken = findViewById(R.id.tokenText)
        buttonVoteMovie = findViewById(R.id.buttonVoteMovie)
        buttonVoteDirector = findViewById(R.id.buttonVoteDirector)
        buttonConfirmVote = findViewById(R.id.buttonConfirmVote)
        buttonLogout = findViewById(R.id.buttonLogout)

        editTextToken.setText(token.toString())

        buttonVoteMovie.setOnClickListener {
            Toast.makeText(this, "Votar Filme clicado", Toast.LENGTH_SHORT).show()
        }

        buttonVoteDirector.setOnClickListener {
            Toast.makeText(this, "Votar Diretor clicado", Toast.LENGTH_SHORT).show()
        }

        buttonConfirmVote.setOnClickListener {
            Toast.makeText(this, "Confirmar Voto clicado", Toast.LENGTH_SHORT).show()
        }

        buttonLogout.setOnClickListener {
            logout("Logout efetuado")
        }
    }

    private fun logout(msg: String) {
        val userPrefs = getSharedPreferences(userPrefsName, Context.MODE_PRIVATE)
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        userPrefs.edit().clear().apply()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
