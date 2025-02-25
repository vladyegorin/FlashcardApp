package com.example.myapplication

import android.content.Intent

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.FlashcardSet
import com.example.myapplication.data.FlashcardDatabase
import com.example.myapplication.data.FlashcardSetAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SeeSetsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FlashcardSetAdapter

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seesets)
        val backButton = findViewById<Button>(R.id.backButton)

        recyclerView = findViewById(R.id.recyclerViewSets)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load flashcard sets from the database
        loadSets()
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close this activity
        }
    }

    private fun loadSets() {
        GlobalScope.launch {
            val sets = FlashcardDatabase.getInstance(applicationContext)
                .flashcardsetDao()
                .getAllFlashcardSets()

            // Log the size of the retrieved list
            android.util.Log.d("SeeSetsActivity", "Number of sets: ${sets.size}")

            // Update RecyclerView on the main thread
            runOnUiThread {
                adapter = FlashcardSetAdapter(
                    sets = sets,
                    onItemClick = { flashcardSet ->
                        // Handle the click event for a flashcard set
                        val intent = Intent(this@SeeSetsActivity, InsideSetActivity::class.java).apply {
                            putExtra("setId", flashcardSet.id) // Pass the set ID to the next activity
                        }
                        startActivity(intent)
                        finish()
                    },
                    onDeleteClick = { flashcardSet ->
                        // Handle the delete button click
                        deleteSet(flashcardSet)
                    }, viewType = 1
                )
                recyclerView.adapter = adapter
            }
        }
    }

    private fun deleteSet(flashcardSet: FlashcardSet) {
        GlobalScope.launch {
            // Delete the set from the database
            FlashcardDatabase.getInstance(applicationContext)
                .flashcardsetDao()
                .delete(flashcardSet)

            // Reload the sets after deletion
            loadSets()
        }


    }
}