package com.example.myapplication.data

import androidx.room.*


@Dao
interface FlashcardSetDao {

    @Insert
    suspend fun insertFlashcardSet(flashcardSet: FlashcardSet)

    @Query("SELECT * FROM flashcard_sets")
    suspend fun getAllFlashcardSets(): List<FlashcardSet>
}