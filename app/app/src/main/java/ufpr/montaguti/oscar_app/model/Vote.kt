package ufpr.montaguti.oscar_app.model

data class Vote(
    val best_movie_id: String,
    val best_director_id: String,
    val payload: String,
    val voting_token: String
)