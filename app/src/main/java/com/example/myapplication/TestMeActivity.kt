package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
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
        val nextButton = findViewById<ImageButton>(R.id.buttonNext)
        val prevButton = findViewById<ImageButton>(R.id.buttonPrev)

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


            var i = 0;
            var cardPosition = true;
            val cardView: CardView = findViewById(R.id.bigPurpleSquare)
            adaptiveTextView.text = "Question: ${questions[i]}"
            cardView.setOnClickListener {
                cardPosition = !cardPosition
                if (cardPosition) {
                    adaptiveTextView.text = "Question: ${questions[i]}"

                } else {
                    adaptiveTextView.text = "Answer: ${answers[i]}"
                }
                //setNameTextView.text = "Question: ${questions[1]}\nAnswer: ${answers[1]}"
                //adaptiveTextView.text = "Question: ${questions[i]}\\n"

            }
            prevButton.visibility = Button.INVISIBLE

            cardView.setOnClickListener {
                cardPosition = !cardPosition
                adaptiveTextView.text = if (cardPosition) {
                    "Question: ${questions[i]}"
                } else {
                    "Answer: ${answers[i]}"
                }
            }

            fun updateButtonVisibility() {
                prevButton.visibility = if (i == 0) Button.INVISIBLE else Button.VISIBLE
                nextButton.visibility = if (i == questions.size - 1) Button.INVISIBLE else Button.VISIBLE
            }

            nextButton.setOnClickListener {
                if (i < questions.size - 1) {
                    i++
                    adaptiveTextView.text = "Question: ${questions[i]}"
                }
                updateButtonVisibility()
            }

            prevButton.setOnClickListener {
                if (i > 0) {
                    i--
                    adaptiveTextView.text = "Question: ${questions[i]}"
                }
                updateButtonVisibility()
            }
        }
        }
    }



//separate function that mixes up questions and answers
//button visibility on first and last question(have a currCard variable if 0 left button invis, if n-1 right button invis)
//will have mixed arrays, and accessed by mixedQ[currCard] and mixedA[currCard]
//make onclick of bigpurplesquare to switch question and answer
//make onclick to navigate left and right