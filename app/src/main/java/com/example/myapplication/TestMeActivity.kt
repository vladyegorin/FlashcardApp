package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.FlashcardDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestMeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testingknowledge)

        val setId = intent.getLongExtra("setId", -1L)
        val setName = intent.getStringExtra("setName")
        if (setId == -1L) {
            // Invalid set ID, handle this case as needed
            finish()
            return
        }

        val setNameTextView = findViewById<TextView>(R.id.chigibam)
        setNameTextView.text = "Set name: $setName"
        val adaptiveTextView = findViewById<TextView>(R.id.adaptiveText)
        val questions = mutableListOf<String>()
        val answers = mutableListOf<String>()

        // Fetch flashcards for the set and populate the lists
        lifecycleScope.launch {
            val flashcards = withContext(Dispatchers.IO) {
                FlashcardDatabase.getInstance(applicationContext)
                    .flashcardDao()
                    .getFlashcardsBySetId(setId) // Fetch flashcards by setId
            }


            for (flashcard in flashcards) {
                questions.add(flashcard.question)
                answers.add(flashcard.answer)
            }

            // Log the lists for debugging
            android.util.Log.d("TestMeActivity", "Questions: $questions")
            android.util.Log.d("TestMeActivity", "Answers: $answers")


            if (questions.isNotEmpty() && answers.isNotEmpty()) {
                setNameTextView.text = "Question: ${questions[1]}\nAnswer: ${answers[1]}"
                adaptiveTextView.text = "lfsdfsdwer sdfsdfe errwr qq rwe";
            }
        }
    }
}