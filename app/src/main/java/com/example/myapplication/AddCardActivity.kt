package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myapplication.data.Flashcard
import com.example.myapplication.data.FlashcardDatabase
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcard)

        val backButton = findViewById<Button>(R.id.backButton)
        val setName = intent.getStringExtra("name")
        val setTotalCardNumber = intent.getIntExtra("number", 0)
        val currentCardNumber = intent.getIntExtra("currnumber", 1)
        val setId = intent.getLongExtra("setId", -1)

        val database = Room.databaseBuilder(
            applicationContext,
            FlashcardDatabase::class.java,
            "flashcard_database"
        ) .build()

        val flashcardDao = database.flashcardDao()

        val numberTextView = findViewById<TextView>(R.id.cardCount)
        numberTextView.text = "$currentCardNumber/$setTotalCardNumber"

        val cardQuestion = findViewById<TextInputEditText>(R.id.questionInput)
        val cardAnswer = findViewById<TextInputEditText>(R.id.answerInput)
        val addCardButton = findViewById<Button>(R.id.createFlashcardButton)

        addCardButton.setOnClickListener {
            var validQ = cardQuestion.text.toString().length < 50 && cardQuestion.text.toString().isNotEmpty()
            var validA = cardAnswer.text.toString().length < 50 && cardAnswer.text.toString().isNotEmpty()

            if (!validQ) Toast.makeText(this, "Enter a valid question.", Toast.LENGTH_LONG).show()
            if (!validA) Toast.makeText(this, "Enter a valid answer.", Toast.LENGTH_LONG).show()

            if (validQ && validA && currentCardNumber <= setTotalCardNumber) {
                val newFlashcard = Flashcard(
                    question = cardQuestion.text.toString(),
                    answer = cardAnswer.text.toString(),
                    setId = setId // Now this is already a Long
                )

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        // Ensure the setId exists in the FlashcardSet table
                        val setExists = flashcardDao.getSetById(setId) != null
                        if (setExists) {
                            flashcardDao.insertFlashcard(newFlashcard)
                            withContext(Dispatchers.Main) {
                                if (currentCardNumber < setTotalCardNumber) {
                                    val intent = Intent(this@AddCardActivity, AddCardActivity::class.java).apply {
                                        putExtra("name", setName)
                                        putExtra("currnumber", currentCardNumber + 1)
                                        putExtra("number", setTotalCardNumber)
                                        putExtra("setId", setId)
                                    }
                                    startActivity(intent)
                                } else {
                                    startActivity(Intent(this@AddCardActivity, SeeSetsActivity::class.java))
                                }
                                finish() // Close the current activity to prevent stacking
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@AddCardActivity, "Flashcard Set does not exist", Toast.LENGTH_LONG).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddCardActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, AddCardSETActivity::class.java)
            startActivity(intent)
        }
    }
}
