package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import kotlin.reflect.typeOf

class QuizQuestionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizquestion)

        val backButton = findViewById<Button>(R.id.backButton)
        val questionNumber = findViewById<TextView>(R.id.questionCount)
        val questionTextView = findViewById<TextView>(R.id.chigibam)
        val answersResults = MutableList(5) { false }
        val nextButton = findViewById<Button>(R.id.nextButton)

        val questions = intent.getStringArrayExtra("questions")
        val answersList = intent.getSerializableExtra("answers") as? List<List<String>>
        val answers = answersList?.map { it.toTypedArray() }?.toTypedArray()
        questionNumber.text = "1"//do this smarter later

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
        println("answers" + answers)
        ans1.text = answers?.getOrNull(i)?.getOrNull(0)?.toString() ?: "Default Text"
        ans2.text = answers?.getOrNull(i)?.getOrNull(1)?.toString() ?: "Default Text"
        ans3.text = answers?.getOrNull(i)?.getOrNull(2)?.toString() ?: "Default Text"
        ans4.text = answers?.getOrNull(i)?.getOrNull(3)?.toString() ?: "Default Text"

        // Variable to keep track of the currently selected answer
        var selectedAnswer: CardView? = null // Initially no answer selected


        answer1.setOnClickListener {
            // Deselect the previously selected answer
            selectedAnswer?.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.default_answer_color
                )
            )

            // Select the new answer
            selectedAnswer = answer1
            selectedAnswer?.setBackgroundResource(R.drawable.glow_effect) // Apply glow effect when selected
        }


        answer2.setOnClickListener {
            // Deselect the previously selected answer
            selectedAnswer?.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.default_answer_color
                )
            )

            // Select the new answer
            selectedAnswer = answer2
            selectedAnswer?.setBackgroundResource(R.drawable.glow_effect) // Apply glow effect when selected
        }


        answer3.setOnClickListener {
            // Deselect the previously selected answer
            selectedAnswer?.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.default_answer_color
                )
            )

            // Select the new answer
            selectedAnswer = answer3
            selectedAnswer?.setBackgroundResource(R.drawable.glow_effect) // Apply glow effect when selected
        }


        answer4.setOnClickListener {
            // Deselect the previously selected answer
            selectedAnswer?.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.default_answer_color
                )
            )

            // Select the new answer
            selectedAnswer = answer4
            selectedAnswer?.setBackgroundResource(R.drawable.glow_effect) // Apply glow effect when selected
        }
        nextButton.setOnClickListener {
            if (questions != null && selectedAnswer != null) {
                if (i < questions.size - 1) {
                    i++
                    questionNumber.text = (i + 1).toString()
                    questionTextView.text = questions?.get(i)?.toString() ?: "Default Text"
                    ans1.text = answers?.getOrNull(i)?.getOrNull(0)?.toString() ?: "Default Text"
                    ans2.text = answers?.getOrNull(i)?.getOrNull(1)?.toString() ?: "Default Text"
                    ans3.text = answers?.getOrNull(i)?.getOrNull(2)?.toString() ?: "Default Text"
                    ans4.text = answers?.getOrNull(i)?.getOrNull(3)?.toString() ?: "Default Text"
                    selectedAnswer?.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.default_answer_color
                        )
                    )
                }
            }


        }
        backButton.setOnClickListener {
            if (questions != null && selectedAnswer != null) {
                if (i > 0) {
                    i--
                    questionNumber.text = (i + 1).toString()
                    questionTextView.text = questions?.get(i)?.toString() ?: "Default Text"
                    ans1.text = answers?.getOrNull(i)?.getOrNull(0)?.toString() ?: "Default Text"
                    ans2.text = answers?.getOrNull(i)?.getOrNull(1)?.toString() ?: "Default Text"
                    ans3.text = answers?.getOrNull(i)?.getOrNull(2)?.toString() ?: "Default Text"
                    ans4.text = answers?.getOrNull(i)?.getOrNull(3)?.toString() ?: "Default Text"
                    selectedAnswer?.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.default_answer_color
                        )
                    )
                }
            }
        }
    }
}
