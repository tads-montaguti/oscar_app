package ufpr.montaguti.oscar_app.ui.movies

import ufpr.montaguti.oscar_app.model.Movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ufpr.montaguti.oscar_app.R
import ufpr.montaguti.oscar_app.ui.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: MoviesAdapter
    private val movieList = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val backButton: Button = findViewById(R.id.backButton)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MoviesAdapter(movieList) { movie ->
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("movieId", movie.id)
            intent.putExtra("movieName", movie.nome)
            intent.putExtra("movieGenre", movie.genero)
            intent.putExtra("movieImageUrl", movie.foto)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        fetchMovies()
    }

    private fun fetchMovies() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        val call = RetrofitInstance.api.getMovies()
        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful && response.body() != null) {
                    movieList.clear()
                    movieList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                } else {
                    showError("Erro ao buscar os filmes.")
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                showError("Falha na conexão: ${t.message}")
            }
        })
    }

    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
