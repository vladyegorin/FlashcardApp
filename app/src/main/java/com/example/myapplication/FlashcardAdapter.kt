package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Flashcard

class FlashcardAdapter(private var flashcards: List<Flashcard>) :
    RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>() {

    class FlashcardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionTextView: TextView = view.findViewById(R.id.questionTextView)
        val answerTextView: TextView = view.findViewById(R.id.answerTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard_item, parent, false)
        return FlashcardViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        val flashcard = flashcards[position]
        holder.questionTextView.text = flashcard.question
        holder.answerTextView.text = flashcard.answer
    }

    override fun getItemCount(): Int {
        return flashcards.size
    }

    // Add this method to update the list of flashcards
    fun updateList(newList: List<Flashcard>) {
        flashcards = newList
        notifyDataSetChanged() // Notify the adapter that data has changed
    }
}