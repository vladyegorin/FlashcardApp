package com.example.myapplication.data

import androidx.room.*


@Dao
interface FlashcardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(flashcard: Flashcard): Long

    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)

    @Query("SELECT * FROM flashcards WHERE setId = :setId")
    suspend fun getFlashcardsForSet(setId: Int): List<Flashcard>
}