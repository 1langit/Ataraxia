package com.cemerlang.ataraxia.ui.journal

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cemerlang.ataraxia.R
import com.cemerlang.ataraxia.data.models.journal.JournalData
import com.cemerlang.ataraxia.databinding.ComponentJournalBinding

class JournalAdapter(
    private val context: Context,
    private val onClick: (JournalData) -> Unit
) : RecyclerView.Adapter<JournalAdapter.ItemJournalViewHolder>() {

    private val journalList = ArrayList<JournalData>()

    inner class ItemJournalViewHolder(private val binding: ComponentJournalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(journal: JournalData) {
            with(binding) {
                txtTitle.text = journal.title
                txtMood.text = journal.mood.replaceFirstChar { it.uppercase() }
                txtDateTime.text = journal.created_at

                imgMood.setImageResource(
                    when (journal.mood.replaceFirstChar { it.uppercase() }) {
                        context.getString(R.string.angst_scale_1) -> R.drawable.ic_mood_sangat_baik
                        context.getString(R.string.angst_scale_2) -> R.drawable.ic_mood_baik
                        context.getString(R.string.angst_scale_3) -> R.drawable.ic_mood_biasa
                        context.getString(R.string.angst_scale_4) -> R.drawable.ic_mood_buruk
                        context.getString(R.string.angst_scale_5) -> R.drawable.ic_mood_sangat_buruk
                        else -> 0
                    }
                )
            }

            itemView.setOnClickListener {
                onClick(journal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalAdapter.ItemJournalViewHolder {
        val binding = ComponentJournalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemJournalViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return journalList.size
    }

    override fun onBindViewHolder(holder: JournalAdapter.ItemJournalViewHolder, position: Int) {
        holder.bind(journalList[position])
    }

    fun setJournals(journals: List<JournalData>) {
        journalList.clear()
        journalList.addAll(journals)
        notifyDataSetChanged()
    }
}