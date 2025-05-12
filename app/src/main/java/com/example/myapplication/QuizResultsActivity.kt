package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizResultsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizresults)

        val score = intent.getIntExtra("score",0)
        val numOfQ = intent.getIntExtra("numofq",5)


        val numberTextView = findViewById<TextView>(R.id.score)
        numberTextView.text = "$score/$numOfQ"



        val doneButton = findViewById<Button>(R.id.doneButton)
        doneButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

}