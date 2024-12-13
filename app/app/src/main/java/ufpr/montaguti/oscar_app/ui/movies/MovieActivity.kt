package ufpr.montaguti.oscar_app.ui.movies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ufpr.montaguti.oscar_app.R
import ufpr.montaguti.oscar_app.model.Movie
import ufpr.montaguti.oscar_app.utils.VoteManager

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movieId = intent.getStringExtra("movieId") ?: ""
        val movieName = intent.getStringExtra("movieName") ?: "Nome não disponível"
        val movieGenre = intent.getStringExtra("movieGenre") ?: "Gênero não disponível"
        val movieImageUrl = intent.getStringExtra("movieImageUrl") ?: ""

        val backButton: Button = findViewById(R.id.backButton)
        val movieImageView: ImageView = findViewById(R.id.movieImageView)
        val movieNameTextView: TextView = findViewById(R.id.movieNameTextView)
        val movieGenreTextView: TextView = findViewById(R.id.movieGenreTextView)
        val voteButton: Button = findViewById(R.id.voteButton)

        movieNameTextView.text = movieName
        movieGenreTextView.text = movieGenre
        Glide.with(this).load(movieImageUrl).into(movieImageView)

        backButton.setOnClickListener {
            finish()
        }

        voteButton.isEnabled = !VoteManager.isVoteSubmitted

        voteButton.setOnClickListener {
            val selectedMovie = Movie(
                id = movieId,
                nome = movieName,
                genero = movieGenre,
                foto = movieImageUrl
            )
            VoteManager.selectedMovie = selectedMovie
            Toast.makeText(this, "Você selecionou ${movieName}", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}