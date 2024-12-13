package ufpr.montaguti.oscar_app.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ufpr.montaguti.oscar_app.R
import ufpr.montaguti.oscar_app.model.Director
import ufpr.montaguti.oscar_app.model.Movie
import ufpr.montaguti.oscar_app.model.Vote
import ufpr.montaguti.oscar_app.utils.VoteManager
import com.google.gson.Gson
import ufpr.montaguti.oscar_app.model.MovieWithDirector

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var movieChosenTextView: TextView
    private lateinit var directorChosenTextView: TextView
    private lateinit var tokenInput: EditText
    private lateinit var submitVoteButton: Button

    private var userPrefsName: String = "LoggedUser"
    private var votePrefsName: String = "VoteSubmited"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val movie = VoteManager.selectedMovie
        val director = VoteManager.selectedDirector

        val backButton: Button = findViewById(R.id.backButton)
        movieChosenTextView = findViewById(R.id.movieChosen)
        directorChosenTextView = findViewById(R.id.directorChosen)
        tokenInput = findViewById(R.id.tokenInput)
        submitVoteButton = findViewById(R.id.submitVoteButton)

        movieChosenTextView.text = movie?.nome ?: "não selecionado"
        directorChosenTextView.text = director?.nome ?: "não selecionado"

        backButton.setOnClickListener { finish() }

        tokenInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSubmitButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        submitVoteButton.setOnClickListener {
            if (movie == null || director == null) {
                Toast.makeText(this, "Filme ou Diretor não selecionado!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val payload = generatePayload(movie, director)
            val vote = Vote(
                best_movie_id = movie.id,
                best_director_id = director.id,
                payload = payload,
                voting_token = tokenInput.text.toString()
            )

            sendVote(vote)
        }
    }

    private fun updateSubmitButtonState() {
        val isTokenFilled = !tokenInput.text.isNullOrEmpty()
        submitVoteButton.isEnabled =
            isTokenFilled && VoteManager.selectedMovie != null && VoteManager.selectedDirector != null && !VoteManager.isVoteSubmitted
    }

    private fun generatePayload(movie: Movie, director: Director): String {
        val payload = MovieWithDirector(movie, director)
        return Gson().toJson(payload)
    }

    private fun sendVote(vote: Vote) {
        val sharedPrefs = getSharedPreferences(userPrefsName, Context.MODE_PRIVATE)
        val authToken = sharedPrefs.getString("LOGGED_USER_AUTH_TOKEN", "") ?: ""
        val userEmail = sharedPrefs.getString("LOGGED_USER_EMAIL", "") ?: ""

        val call = RetrofitInstance.voteService.create(authToken, userEmail, vote)
        call.enqueue(object : Callback<Vote> {
            override fun onResponse(call: Call<Vote>, response: Response<Vote>) {
                if (response.isSuccessful) {
                    val prefsVote = getSharedPreferences(votePrefsName, Context.MODE_PRIVATE)
                    val editor = prefsVote.edit()
                    editor.putString("VOTE", vote.payload)
                    editor.apply()

                    VoteManager.isVoteSubmitted = true
                    Toast.makeText(this@ConfirmationActivity, "OK", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    showErrorDialog("Erro ao enviar voto: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Vote>, t: Throwable) {
                showErrorDialog("Falha na conexão: ${t.message}")
            }
        })
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Erro")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}