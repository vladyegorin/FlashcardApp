package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FlashcardSetDao {
    @Insert
    suspend fun insertFlashcardSet(flashcardSet: FlashcardSet): Long

    @Query("SELECT * FROM flashcard_sets WHERE id = :setId")
    suspend fun getFlashcardSetById(setId: Long): FlashcardSet?

    @Query("SELECT * FROM flashcard_sets") // Replace `flashcard_sets` with your table name
    fun getAllFlashcardSets(): List<FlashcardSet>

    @Delete
    suspend fun delete(set: FlashcardSet)
}
