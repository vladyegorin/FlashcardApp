package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.FlashcardDatabase
import com.example.myapplication.data.FlashcardSetAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ChooseSetToTestOn : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FlashcardSetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testme)


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
            Log.d("SeeSetsActivity", "Number of sets: ${sets.size}")

            // Update RecyclerView on the main thread
            runOnUiThread {
                adapter = FlashcardSetAdapter(
                    sets = sets,
                    onItemClick = { flashcardSet ->
                        val intent =
                            Intent(this@ChooseSetToTestOn, TestMeActivity::class.java).apply {
                                putExtra(
                                    "setId",
                                    flashcardSet.id

                                )
                                putExtra(
                                    "setName",
                                    flashcardSet.setName
                                )
                            }
                        startActivity(intent)
                        finish()
                    },
                    onDeleteClick = {},//will probably remain empty
                    viewType = 2
                )
                recyclerView.adapter = adapter
            }
        }
    }


}

