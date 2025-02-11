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
    private val onDeleteClick: (FlashcardSet) -> Unit,
    private val viewType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_WITH_DELETE = 1
        private const val VIEW_TYPE_WITHOUT_DELETE = 2
    }

    // ViewHolder for layout with delete button
    inner class ViewHolderWithDelete(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setName: TextView = itemView.findViewById(R.id.textViewSetName)
        val setSize: TextView = itemView.findViewById(R.id.textViewSetSize)
        val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDeleteSet)
    }

    // ViewHolder for layout without delete button
    inner class ViewHolderWithoutDelete(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setName: TextView = itemView.findViewById(R.id.textViewSetName)
        val setSize: TextView = itemView.findViewById(R.id.textViewSetSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_WITH_DELETE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_flashcard_set, parent, false)
                ViewHolderWithDelete(view)
            }
            VIEW_TYPE_WITHOUT_DELETE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_flashcardset_tochoose, parent, false)
                ViewHolderWithoutDelete(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val set = sets[position]

        when (holder) {
            is ViewHolderWithDelete -> {
                holder.setName.text = set.setName
                holder.setSize.text = "${set.numberOfFlashcards} cards"
                holder.itemView.setOnClickListener { onItemClick(set) }
                holder.deleteButton.setOnClickListener { onDeleteClick(set) }
            }
            is ViewHolderWithoutDelete -> {
                holder.setName.text = set.setName
                holder.setSize.text = "${set.numberOfFlashcards} cards"
                holder.itemView.setOnClickListener { onItemClick(set) }
            }
        }
    }

    override fun getItemCount(): Int = sets.size

    override fun getItemViewType(position: Int): Int {
        return viewType
    }
}