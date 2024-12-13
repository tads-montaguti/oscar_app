package ufpr.montaguti.oscar_app.model

data class User(
    val id: Int,
    val name: String,
    val voting_token: Int,
    val email: String,
    val authentication_token: String
)