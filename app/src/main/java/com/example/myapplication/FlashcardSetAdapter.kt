package com.example.myapplication.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class FlashcardSetAdapter(private val sets: List<FlashcardSet>) :
    RecyclerView.Adapter<FlashcardSetAdapter.FlashcardSetViewHolder>() {

    inner class FlashcardSetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setName: TextView = itemView.findViewById(R.id.textViewSetName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardSetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard_set, parent, false)
        return FlashcardSetViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashcardSetViewHolder, position: Int) {
        val set = sets[position]
        holder.setName.text = set.setName
    }

    override fun getItemCount(): Int = sets.size
}
