package ufpr.montaguti.oscar_app.utils

import ufpr.montaguti.oscar_app.model.Director
import ufpr.montaguti.oscar_app.model.Movie

object VoteManager {
    var selectedMovie: Movie? = null
    var selectedDirector: Director? = null
    var isVoteSubmitted: Boolean = false

    fun destroy() {
        selectedMovie = null
        selectedDirector = null
        isVoteSubmitted = false
    }
}