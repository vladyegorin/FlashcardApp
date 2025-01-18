package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "flashcards",
    foreignKeys = [
        ForeignKey(
            entity = FlashcardSet::class,
            parentColumns = ["setId"],
            childColumns = ["setId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Flashcard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val answer: String,
    val setId: Int        // ID of the flashcard set this belongs to
)
