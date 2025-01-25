package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.FlashcardDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InsideSetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insideset)

        val backButton = findViewById<Button>(R.id.backButton)
        val setName = intent.getStringExtra("name")
        val setTotalCardNumber = intent.getIntExtra("number", 0)
        val currentCardNumber = intent.getIntExtra("currnumber", 1)
        val setId = intent.getLongExtra("setId", -1L) // Ensure it's fetched as Long

        if (setId == -1L) {
            // Invalid set ID, exit the activity
            finish()
            return
        }

        val numberTextView = findViewById<TextView>(R.id.cardCount)
        numberTextView.text = "$currentCardNumber/$setTotalCardNumber"

        // Ensure flashcards are fetched for the correct set
        lifecycleScope.launch {
            try {
                val flashcards = withContext(Dispatchers.IO) {
                    FlashcardDatabase.getInstance(applicationContext)
                        .flashcardDao()
                        .getFlashcardsBySetId(setId) // setId should be Long here
                }

                // Handle the fetched flashcards here (e.g., displaying them in a list)

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@InsideSetActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, SeeSetsActivity::class.java)
            startActivity(intent)
        }
    }
}
