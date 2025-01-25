package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [FlashcardSet::class, Flashcard::class], version = 2)
abstract class FlashcardDatabase : RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao
    abstract fun flashcardsetDao(): FlashcardSetDao

    companion object {
        @Volatile
        private var INSTANCE: FlashcardDatabase? = null

        fun getInstance(context: Context): FlashcardDatabase {
            return INSTANCE ?: synchronized(this) {
                val dbFile = context.getDatabasePath("flashcard_database")

                // Deleting the database file to reset (if necessary for development)
                // Uncomment if needed
                // if (dbFile.exists()) {
                //     dbFile.delete()
                // }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlashcardDatabase::class.java,
                    "flashcard_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            // Enable foreign key constraints
                            db.execSQL("PRAGMA foreign_keys=ON;")
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
