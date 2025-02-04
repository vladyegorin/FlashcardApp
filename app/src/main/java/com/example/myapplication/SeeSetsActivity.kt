// SeeSetsActivity.kt
package com.example.myapplication

import android.content.Intent
import android.os.Bundle
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
    private lateinit var adapter: FlashcardSetAdapter<FlashcardSet> // Specify type as FlashcardSet

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seesets)

        recyclerView = findViewById(R.id.recyclerViewSets)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch data from the database
        GlobalScope.launch {
            val sets = FlashcardDatabase.getInstance(applicationContext)
                .flashcardsetDao()
                .getAllFlashcardSets()

            // Log the size of the retrieved list
            android.util.Log.d("SeeSetsActivity", "Number of sets: ${sets.size}")

            // Update RecyclerView on the main thread
            runOnUiThread {
                adapter = FlashcardSetAdapter(sets) { flashcardSet ->
                    // Handle the click event for a flashcard set
                    val intent = Intent(this@SeeSetsActivity, InsideSetActivity::class.java).apply {
                        putExtra("setId", flashcardSet.id) // Pass the set ID to the next activity
                        finish()
                    }
                    startActivity(intent)
                }
                recyclerView.adapter = adapter
            }
        }
    }
}
