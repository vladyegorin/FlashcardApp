package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.myapplication.R

class QuizQuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizquestion)

        val questionNumber = findViewById<TextView>(R.id.questionCount)
        val questionTextView = findViewById<TextView>(R.id.chigibam)
        val nextButton = findViewById<Button>(R.id.nextButton)
        var score = 0

        val questions = intent.getStringArrayExtra("questions")
        val answersList = intent.getSerializableExtra("answers") as? List<List<String>>
        val answers = answersList?.map { it.toTypedArray() }?.toTypedArray()

        questionNumber.text = "1"
        questionTextView.text = questions?.get(0)?.toString() ?: "Default Text"

        var i = 0
        val ans1 = findViewById<TextView>(R.id.answerText1)
        val ans2 = findViewById<TextView>(R.id.answerText2)
        val ans3 = findViewById<TextView>(R.id.answerText3)
        val ans4 = findViewById<TextView>(R.id.answerText4)
        val answer1: CardView = findViewById(R.id.answer1)
        val answer2: CardView = findViewById(R.id.answer2)
        val answer3: CardView = findViewById(R.id.answer3)
        val answer4: CardView = findViewById(R.id.answer4)

        println("answers $answers")

        ans1.text = answers?.getOrNull(i)?.getOrNull(0) ?: "Default Text"
        ans2.text = answers?.getOrNull(i)?.getOrNull(1) ?: "Default Text"
        ans3.text = answers?.getOrNull(i)?.getOrNull(2) ?: "Default Text"
        ans4.text = answers?.getOrNull(i)?.getOrNull(3) ?: "Default Text"

        var selectedAnswer: CardView? = null
        var correctAnswer: CardView? = null

        correctAnswer = detectAndStripCorrectAnswer(
            listOf(ans1, ans2, ans3, ans4),
            listOf(answer1, answer2, answer3, answer4)
        )

        answer1.setOnClickListener {
            resetAllAnswerCards()
            selectedAnswer = answer1
            selectedAnswer?.setBackgroundResource(R.drawable.glow_effect)
        }

        answer2.setOnClickListener {
            resetAllAnswerCards()
            selectedAnswer = answer2
            selectedAnswer?.setBackgroundResource(R.drawable.glow_effect)
        }

        answer3.setOnClickListener {
            resetAllAnswerCards()
            selectedAnswer = answer3
            selectedAnswer?.setBackgroundResource(R.drawable.glow_effect)
        }

        answer4.setOnClickListener {
            resetAllAnswerCards()
            selectedAnswer = answer4
            selectedAnswer?.setBackgroundResource(R.drawable.glow_effect)
        }

        nextButton.setOnClickListener {
            if (questions != null && selectedAnswer != null) {
                if (i < questions.size - 1) {
                    if (selectedAnswer == correctAnswer) {
                        score++
                    }

                    resetAllAnswerCards()
                    i++

                    questionNumber.text = (i + 1).toString()
                    questionTextView.text = questions[i]
                    ans1.text = answers?.getOrNull(i)?.getOrNull(0) ?: "Default Text"
                    ans2.text = answers?.getOrNull(i)?.getOrNull(1) ?: "Default Text"
                    ans3.text = answers?.getOrNull(i)?.getOrNull(2) ?: "Default Text"
                    ans4.text = answers?.getOrNull(i)?.getOrNull(3) ?: "Default Text"

                    if (i == 4) {
                        nextButton.text = "Finish"
                        nextButton.setOnClickListener {
                            val intent = Intent(this, QuizResultsActivity::class.java)
                            intent.putExtra("score", score)
                            intent.putExtra("numofq", 5)
                            startActivity(intent)
                        }
                    }

                    correctAnswer = detectAndStripCorrectAnswer(
                        listOf(ans1, ans2, ans3, ans4),
                        listOf(answer1, answer2, answer3, answer4)
                    )
                }
            }
        }
    }

    private fun resetAllAnswerCards() {
        val answer1: CardView = findViewById(R.id.answer1)
        val answer2: CardView = findViewById(R.id.answer2)
        val answer3: CardView = findViewById(R.id.answer3)
        val answer4: CardView = findViewById(R.id.answer4)

        answer1.setBackgroundResource(R.drawable.quiz_answer_background)
        answer2.setBackgroundResource(R.drawable.quiz_answer_background)
        answer3.setBackgroundResource(R.drawable.quiz_answer_background)
        answer4.setBackgroundResource(R.drawable.quiz_answer_background)
    }

    private fun detectAndStripCorrectAnswer(
        textViews: List<TextView>,
        cards: List<CardView>
    ): CardView? {
        for ((index, textView) in textViews.withIndex()) {
            val text = textView.text.toString()
            if (text.endsWith("*")) {
                textView.text = text.dropLast(1)
                return cards[index]
            }
        }
        return null
    }
}
