package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcard_sets")
data class FlashcardSet(
    @PrimaryKey(autoGenerate = true) val setId: Int = 0, // Unique ID for each set
    val setName: String                                  // Name of the flashcard set
)
