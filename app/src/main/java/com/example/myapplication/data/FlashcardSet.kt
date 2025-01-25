package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcard_sets")
data class FlashcardSet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,  // Primary key
    val setName: String,
    val numberOfFlashcards: Int
)
