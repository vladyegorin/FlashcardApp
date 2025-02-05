package com.example.myapplication.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class FlashcardSetAdapter(
    private val sets: List<FlashcardSet>,
    private val onItemClick: (FlashcardSet) -> Unit,
    private val onDeleteClick: (FlashcardSet) -> Unit
) : RecyclerView.Adapter<FlashcardSetAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setName: TextView = itemView.findViewById(R.id.textViewSetName)
        val setSize: TextView = itemView.findViewById(R.id.textViewSetSize)
        val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDeleteSet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard_set, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val set = sets[position]

        // Bind set data
        holder.setName.text = set.setName
        holder.setSize.text = "${set.numberOfFlashcards} cards" // Assuming cardCount exists in FlashcardSet

        // Handle item click
        holder.itemView.setOnClickListener { onItemClick(set) }

        // Handle delete button click
        holder.deleteButton.setOnClickListener { onDeleteClick(set) }
    }

    override fun getItemCount(): Int = sets.size

    // Update data method
    fun updateSets(newSets: List<FlashcardSet>) {
        (sets as MutableList).clear()
        (sets as MutableList).addAll(newSets)
        notifyDataSetChanged()
    }
}