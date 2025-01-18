package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.FlashcardDatabase
import com.example.myapplication.data.FlashcardSetAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private lateinit var recyclerView: RecyclerView
private lateinit var adapter: FlashcardSetAdapter
class SeeSetsActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seesets)

        recyclerView = findViewById(R.id.recyclerViewSets)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch data from the database
        GlobalScope.launch {
            val sets = FlashcardDatabase.getInstance(applicationContext)
                .flashcardSetDao()
                .getAllFlashcardSets()

            // Log the size of the retrieved list
            android.util.Log.d("SeeSetsActivity", "Number of sets: ${sets.size}")

            // Update RecyclerView on the main thread
            runOnUiThread {
                adapter = FlashcardSetAdapter(sets)
                recyclerView.adapter = adapter
            }
        }
    }
}