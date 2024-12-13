package ufpr.montaguti.oscar_app.ui.movies

import android.util.Log
import ufpr.montaguti.oscar_app.model.Movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ufpr.montaguti.oscar_app.R
import kotlin.math.log

class MoviesAdapter(
    private val movies: List<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], onItemClick)
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val genreTextView: TextView = itemView.findViewById(R.id.genreTextView)

        fun bind(movie: Movie, onItemClick: (Movie) -> Unit) {
            nameTextView.text = movie.nome
            genreTextView.text = movie.genero
            Glide.with(itemView.context).load(movie.foto).into(imageView)

            itemView.setOnClickListener { onItemClick(movie) }
        }
    }
}
