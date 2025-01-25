// FlashcardSetAdapter.kt
package com.example.myapplication.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Flashcard

// Generic adapter to handle both FlashcardSet and Flashcard
class FlashcardSetAdapter<T>(
    private val items: List<T>,
    private val onItemClick: (T) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // ViewHolder for FlashcardSet
    inner class FlashcardSetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setName: TextView = itemView.findViewById(R.id.textViewSetName)
    }

    // ViewHolder for Flashcard
    inner class FlashcardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question: TextView = itemView.findViewById(R.id.textViewQuestion)
        val answer: TextView = itemView.findViewById(R.id.textViewAnswer)
    }

    // Override to handle different view types for FlashcardSet and Flashcard
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FlashcardSet -> 0 // For FlashcardSet
            is Flashcard -> 1     // For Flashcard
            else -> throw IllegalArgumentException("Unsupported item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_flashcard_set, parent, false)
                FlashcardSetViewHolder(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_flashcard_item, parent, false)
                FlashcardViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is FlashcardSet -> {
                val setHolder = holder as FlashcardSetAdapter<*>.FlashcardSetViewHolder
                setHolder.setName.text = item.setName
                holder.itemView.setOnClickListener { onItemClick(item) }
            }
            is Flashcard -> {
                val cardHolder = holder as FlashcardSetAdapter<*>.FlashcardViewHolder
                cardHolder.question.text = item.question
                cardHolder.answer.text = item.answer
                holder.itemView.setOnClickListener { onItemClick(item) }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
