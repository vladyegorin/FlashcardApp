package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.FlashcardDatabase
import com.example.myapplication.data.FlashcardSet
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCardSETActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcardset)

        val setNameInput = findViewById<TextInputEditText>(R.id.setNameInput)
        val setNumberInput = findViewById<TextInputEditText>(R.id.setNumberInput)
        val createSetButton = findViewById<Button>(R.id.startCreatingButton)
        val backButton = findViewById<Button>(R.id.backButton)
        // Initialize the database and the DAO for FlashcardSets
        val database = FlashcardDatabase.getInstance(applicationContext)
        val flashcardSetDao = database.flashcardDao() // Corrected DAO access

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        createSetButton.setOnClickListener {
            val setName = setNameInput.text.toString()
            val setNumber = setNumberInput.text.toString().toIntOrNull()

            // Validate the inputs
            if (setName.isEmpty() || setNumber == null || setNumber <= 0 || setNumber >=15) {
                Toast.makeText(this, "Enter valid set details", Toast.LENGTH_LONG).show()
            } else {
                // Create the new FlashcardSet object
                val newSet = FlashcardSet(setName = setName, numberOfFlashcards = setNumber)

                // Use lifecycleScope to launch a coroutine for inserting the new set
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        // Insert the new set into the database
                        val setId = flashcardSetDao.insertFlashcardSet(newSet)

                        // After inserting, navigate to AddCardActivity
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddCardSETActivity, "Set created!", Toast.LENGTH_LONG).show()

                            val intent = Intent(this@AddCardSETActivity, AddCardActivity::class.java).apply {
                                putExtra("name", setName)
                                putExtra("number", setNumber)
                                putExtra("currnumber", 1)
                                putExtra("setId", setId) // Pass the setId to the next activity
                            }
                            startActivity(intent)
                            finish() // Close this activity
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddCardSETActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}
