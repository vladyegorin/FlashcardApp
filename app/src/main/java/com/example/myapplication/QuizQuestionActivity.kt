package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.typeOf

class QuizQuestionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizquestion)

        val backButton = findViewById<Button>(R.id.backButton)
        val questionNumber = findViewById<TextView>(R.id.questionCount)
        val questionTextView = findViewById<TextView>(R.id.chigibam)
        backButton.setOnClickListener {
            val intent = Intent(this, QuizMainActivity::class.java)
            startActivity(intent)
            finish() // Close this activity
        }
        val questions = intent.getStringArrayExtra("questions")
        val answersList = intent.getSerializableExtra("answers") as? List<List<String>>
        val answers = answersList?.map { it.toTypedArray() }?.toTypedArray()
        questionNumber.text = "1"//do this smarter later

        questionTextView.text = questions?.get(0)?.toString() ?: "Default Text"

        val ans1 = findViewById<TextView>(R.id.answerText1)
        val ans2 = findViewById<TextView>(R.id.answerText2)
        val ans3 = findViewById<TextView>(R.id.answerText3)
        val ans4 = findViewById<TextView>(R.id.answerText4)
        println("answers" +answers)
        ans1.text = answers?.getOrNull(0)?.getOrNull(0)?.toString() ?: "Default Text"
        ans2.text = answers?.getOrNull(0)?.getOrNull(1)?.toString() ?: "Default Text"
        ans3.text = answers?.getOrNull(0)?.getOrNull(2)?.toString() ?: "Default Text"
        ans4.text = answers?.getOrNull(0)?.getOrNull(3)?.toString() ?: "Default Text"





    }
}