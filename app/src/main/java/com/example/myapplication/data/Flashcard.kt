package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "flashcards",
    foreignKeys = [
        ForeignKey(
            entity = FlashcardSet::class,
            parentColumns = ["id"],  // Reference to FlashcardSet's id column
            childColumns = ["setId"],  // Foreign key column in Flashcard
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Flashcard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val answer: String,
    val setId: Long  // Foreign key reference to FlashcardSet's id
)

