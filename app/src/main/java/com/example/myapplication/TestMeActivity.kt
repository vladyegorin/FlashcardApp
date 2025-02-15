package com.example.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
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
            finish()
            return
        }

        val setNameTextView = findViewById<TextView>(R.id.chigibam)
        setNameTextView.text = "Set name: $setName"
        val questionTextView = findViewById<TextView>(R.id.questionTextView)
        val answerTextView = findViewById<TextView>(R.id.answerTextView)
        val questions = mutableListOf<String>()
        val answers = mutableListOf<String>()
        val nextButton = findViewById<ImageButton>(R.id.buttonNext)
        val prevButton = findViewById<ImageButton>(R.id.buttonPrev)
        val doneButton = findViewById<Button>(R.id.doneButton)
        val cardView: CardView = findViewById(R.id.bigPurpleSquare)

        doneButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        lifecycleScope.launch {
            val flashcards = withContext(Dispatchers.IO) {
                FlashcardDatabase.getInstance(applicationContext)
                    .flashcardDao()
                    .getFlashcardsBySetId(setId)
            }

            for (flashcard in flashcards) {
                questions.add(flashcard.question)
                answers.add(flashcard.answer)
            }

            shuffleLists(questions, answers)

            var i = 0
            var cardPosition = true
            questionTextView.text = "Question: ${questions[i]}"
            answerTextView.text = "Answer: ${answers[i]}"
            updateButtonVisibility(i, questions.size, prevButton, nextButton)

            cardView.setOnClickListener {
                cardPosition = !cardPosition

                // Swap visibility before flipping
                questionTextView.visibility = if (cardPosition) View.VISIBLE else View.INVISIBLE
                answerTextView.visibility = if (cardPosition) View.INVISIBLE else View.VISIBLE

                val flipAnimation = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f)
                flipAnimation.duration = 150
                flipAnimation.interpolator = DecelerateInterpolator()

                flipAnimation.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        // Swap text visibility properly
                        questionTextView.visibility = if (cardPosition) View.VISIBLE else View.INVISIBLE
                        answerTextView.visibility = if (cardPosition) View.INVISIBLE else View.VISIBLE

                        // Continue the flip to restore the original scale
                        val flipBack = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f)
                        flipBack.duration = 150
                        flipBack.interpolator = DecelerateInterpolator()
                        flipBack.start()
                    }
                })

                flipAnimation.start()
            }





            nextButton.setOnClickListener {
                if (i < questions.size - 1) {
                    i++
                    questionTextView.text = "Question: ${questions[i]}"
                    answerTextView.text = "Answer: ${answers[i]}"
                    cardPosition = true // Reset to question when moving to the next card
                }
                updateButtonVisibility(i, questions.size, prevButton, nextButton)
            }

            prevButton.setOnClickListener {
                if (i > 0) {
                    i--
                    questionTextView.text = "Question: ${questions[i]}"
                    answerTextView.text = "Answer: ${answers[i]}"
                    cardPosition = true // Reset to question when moving to the previous card
                }
                updateButtonVisibility(i, questions.size, prevButton, nextButton)
            }
        }
    }

    private fun shuffleLists(questions: MutableList<String>, answers: MutableList<String>) {
        val combined = questions.zip(answers).shuffled()
        questions.clear()
        answers.clear()
        questions.addAll(combined.map { it.first })
        answers.addAll(combined.map { it.second })
    }

    private fun updateButtonVisibility(
        currentIndex: Int,
        totalSize: Int,
        prevButton: ImageButton,
        nextButton: ImageButton
    ) {
        prevButton.visibility = if (currentIndex == 0) Button.INVISIBLE else Button.VISIBLE
        nextButton.visibility = if (currentIndex == totalSize - 1) Button.INVISIBLE else Button.VISIBLE
    }
}