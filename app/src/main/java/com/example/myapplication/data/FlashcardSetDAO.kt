package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FlashcardSetDao {

    @Insert
    suspend fun insertFlashcardSet(flashcardSet: FlashcardSet)

    @Query("SELECT * FROM flashcard_sets")
    suspend fun getAllFlashcardSets(): List<FlashcardSet>
}