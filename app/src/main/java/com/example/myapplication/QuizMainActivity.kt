package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class QuizMainActivity : AppCompatActivity() {

    private lateinit var groq: Groq

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizmain)

        // Initialize the Groq class with the context
        groq = Groq(this)
        val quizTopic = findViewById<TextInputEditText>(R.id.quizTopicInput)
        val createQuizButton = findViewById<Button>(R.id.createQuizButton)
        //val aians = findViewById<TextView>(R.id.chigibam)

        // Use lifecycleScope to launch a coroutine
        lifecycleScope.launch {
            //val airesp = aiResponse(quizTopic.text.toString()) //how to call the function
            //val responseText = aiResponse("quick snack recipe")
            //aians.text = responseText
        }
    }

    // Function to send user input to the AI and get a response
    private suspend fun aiResponse(userInput: String): String {
        val aiPrompt = "Your goal is to create a quiz on a given topic. The quiz should contain 5 questions. " +
                "Output the questions and answers in the following format: question1:answer1,answer2,answer3,asnwer4 and add a * sign to the correct answer" +
                "Make the questions pretty easy the topic is: $userInput" +
                "do not output anything else but the quiz." +
                "do not output the word question, just do the actual question: and answers divided by comas"
        return try {
            // Use a background thread to call the Groq API
            withContext(Dispatchers.IO) {
                groq.sendMessage(userInput)
            }
        } catch (e: IOException) {
            // Handle network or API errors
            "Error: ${e.localizedMessage}"
        }
    }
}