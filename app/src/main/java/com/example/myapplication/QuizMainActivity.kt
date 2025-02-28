package com.example.myapplication

import android.content.Intent
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
import java.io.Serializable

class QuizMainActivity : AppCompatActivity() {

    private lateinit var groq: Groq
    private lateinit var questionsSplit: Array<String>
    private lateinit var answersSplit: Array<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizmain)

        // Initialize the Groq class with the context
        groq = Groq(this)
        val quizTopic = findViewById<TextInputEditText>(R.id.quizTopicInput)
        val createQuizButton = findViewById<Button>(R.id.createQuizButton)
        val backButton = findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close this activity
        }
        // Use lifecycleScope to launch a coroutine
        createQuizButton.setOnClickListener {

            lifecycleScope.launch {
                val airesp = aiResponse(quizTopic.text.toString()) //how to call the function
                println(airesp)
                val aiAns = airesp.split(":").toTypedArray();
                val questionsSplit = aiAns[0].split(",").toTypedArray();//1D array
                val answersSplit = aiAns[1].split("/").map { it.split(";").toTypedArray() }
                    .toTypedArray() // 2D array

                // Convert 2D array to List<List<String>> for serialization
                val answersList = answersSplit.map { it.toList() }.toList()


                val intent = Intent(this@QuizMainActivity, QuizQuestionActivity::class.java)
                intent.putExtra("questions", questionsSplit)
                intent.putExtra("answers", answersList as Serializable)
                startActivity(intent)
                finish()
            }



        }

    }

    // Function to send user input to the AI and get a response
    private suspend fun aiResponse(userInput: String): String {
        val aiPrompt = """
    Create a quiz on the following topic: $userInput. The quiz must contain exactly 5 questions, each with 4 possible answers, where only one answer is correct. 
    
    Format your output as follows:
    - List all 5 questions, separated by commas.
    - After the 5th question, add a colon (":").
    - Then list all answer choices, where each set of 4 answers is separated by a slash ("/").
    - Within each set, separate the 4 answers using semicolons (";").
    - Mark the correct answer with an asterisk ("*") immediately after it.
    - Do not include the question name before listing answers, it is already listed in the first line
    Example Output Format:
    Question1,Question2,Question3,Question4,Question5 : Answer1-1;Answer1-2;Answer1-3;Answer1-4*/Answer2-1;Answer2-2*;Answer2-3;Answer2-4/...
    
    Rules:
    - Do not include the word "Question" before each question.
    - Do not include any additional text in the response.
    - Ensure that each question has exactly one correct answer, marked with "*".
    - Make the questions easy as per the topic.
    
""".trimIndent()


        return try {
            // Use a background thread to call the Groq API
            withContext(Dispatchers.IO) {
                groq.sendMessage(aiPrompt)
            }
        } catch (e: IOException) {
            // Handle network or API errors
            "Error: ${e.localizedMessage}"
        }
    }
}