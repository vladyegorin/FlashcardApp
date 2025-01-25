package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FlashcardDao {
    @Insert
    suspend fun insertFlashcardSet(flashcardSet: FlashcardSet): Long

    @Insert
    suspend fun insertFlashcard(flashcard: Flashcard)

    @Query("SELECT * FROM flashcard_sets")
    suspend fun getAllFlashcardSets(): List<FlashcardSet>

    @Query("SELECT * FROM flashcards WHERE setId = :setId")
    suspend fun getFlashcardsBySetId(setId: Long): List<Flashcard>

    @Query("SELECT * FROM flashcard_sets WHERE id = :setId")
    suspend fun getSetById(setId: Long): FlashcardSet?

}
