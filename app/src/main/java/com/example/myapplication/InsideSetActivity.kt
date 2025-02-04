package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.FlashcardDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InsideSetActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FlashcardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insideset)

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewFlashcards)
        val backButton = findViewById<Button>(R.id.backButton)

        // Fetch the setId from the intent
        val setId = intent.getLongExtra("setId", -1L)
        if (setId == -1L) {
            // Invalid set ID, exit the activity
            Toast.makeText(this, "Invalid flashcard set", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FlashcardAdapter(emptyList()) // Initialize with an empty list
        recyclerView.adapter = adapter

        // Fetch flashcards for the set
        lifecycleScope.launch {
            try {
                val flashcards = withContext(Dispatchers.IO) {
                    FlashcardDatabase.getInstance(applicationContext)
                        .flashcardDao()
                        .getFlashcardsBySetId(setId) // Fetch flashcards by setId
                }

                // Update the adapter with the fetched flashcards
                adapter.updateList(flashcards)

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@InsideSetActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Handle back button click
        backButton.setOnClickListener {
            val intent = Intent(this, SeeSetsActivity::class.java)
            startActivity(intent)
            finish() // Close this activity
        }
    }
}