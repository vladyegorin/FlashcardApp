package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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

        val aians = findViewById<TextView>(R.id.chigibam)

        // Use lifecycleScope to launch a coroutine
        lifecycleScope.launch {
            val responseText = aiResponse("quick snack recipe")
            aians.text = responseText
        }
    }

    // Function to send user input to the AI and get a response
    private suspend fun aiResponse(userInput: String): String {
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