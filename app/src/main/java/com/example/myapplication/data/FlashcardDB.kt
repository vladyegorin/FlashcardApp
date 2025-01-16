package com.example.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [FlashcardSet::class, Flashcard::class],
    version = 1,
    exportSchema = false
)
abstract class FlashcardDatabase : RoomDatabase() {

    // Define abstract functions to access DAOs
    abstract fun flashcardSetDao(): FlashcardSetDao
    abstract fun flashcardDao(): FlashcardDao

    companion object {
        const val DATABASE_NAME = "flashcard_database"
    }
}