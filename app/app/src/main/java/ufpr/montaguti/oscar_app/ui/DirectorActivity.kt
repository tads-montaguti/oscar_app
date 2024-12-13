package ufpr.montaguti.oscar_app.ui


import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ufpr.montaguti.oscar_app.R
import ufpr.montaguti.oscar_app.model.Director
import ufpr.montaguti.oscar_app.utils.VoteManager

class DirectorActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var radioGroup: RadioGroup
    private lateinit var confirmButton: Button
    private var directors: List<Director> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_director)

        val backButton: Button = findViewById(R.id.backButton)
        progressBar = findViewById(R.id.progressBar)
        radioGroup = findViewById(R.id.directorRadioGroup)
        confirmButton = findViewById(R.id.confirmVoteButton)

        backButton.setOnClickListener { finish() }

        confirmButton.isEnabled = false
        confirmButton.setOnClickListener {
            val checkedRadioButtonId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(checkedRadioButtonId)
            val selectedDirectorName = selectedRadioButton?.text?.toString()

            val selectedDirector = directors.find { it.nome == selectedDirectorName }
            if (selectedDirector != null) {
                VoteManager.selectedDirector = selectedDirector
                Toast.makeText(this, "Voto confirmado para: ${selectedDirector.nome}", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erro ao confirmar voto.", Toast.LENGTH_SHORT).show()
            }
        }

        fetchDirectors()
    }

    private fun fetchDirectors() {
        progressBar.visibility = View.VISIBLE
        radioGroup.visibility = View.GONE
        confirmButton.visibility = View.GONE

        val call = RetrofitInstance.api_2.getDirectors()
        call.enqueue(object : Callback<List<Director>> {
            override fun onResponse(call: Call<List<Director>>, response: Response<List<Director>>) {
                if (response.isSuccessful && response.body() != null) {
                    directors = response.body()!!
                    populateRadioGroup(directors)

                    updateRadioGroupSelection()

                    progressBar.visibility = View.GONE
                    radioGroup.visibility = View.VISIBLE
                    confirmButton.visibility = View.VISIBLE
                    confirmButton.isEnabled = !VoteManager.isVoteSubmitted
                } else {
                    showError("Erro ao carregar diretores.")
                }
            }

            override fun onFailure(call: Call<List<Director>>, t: Throwable) {
                showError("Falha na conexão: ${t.message}")
            }
        })
    }

    private fun populateRadioGroup(directors: List<Director>) {
        radioGroup.removeAllViews()
        for (director in directors) {
            val radioButton = RadioButton(this).apply {
                text = director.nome
                id = director.id.toInt()
            }
            radioGroup.addView(radioButton)
        }
        radioGroup.setOnCheckedChangeListener { _, _ ->
            confirmButton.isEnabled = !VoteManager.isVoteSubmitted
        }
    }

    private fun updateRadioGroupSelection() {
        val selectedDirector = VoteManager.selectedDirector
        if (selectedDirector != null) {
            val radioButton = radioGroup.findViewById<RadioButton>(selectedDirector.id.toInt())
            radioButton?.isChecked = true
            confirmButton.isEnabled = true
        }
    }

    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
