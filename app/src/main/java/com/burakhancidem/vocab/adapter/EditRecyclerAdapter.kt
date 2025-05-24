package com.burakhancidem.vocab.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.burakhancidem.vocab.databinding.RecyclerRowBinding
import com.burakhancidem.vocab.model.Word
import com.burakhancidem.vocab.view.EditFragmentDirections

class EditRecyclerAdapter(private val wordList: ArrayList<Word>): RecyclerView.Adapter<EditRecyclerAdapter.WordHolder>() {

    class WordHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WordHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {

        holder.binding.recyclerEnWord.text = wordList.get(position).editTextEnWord
        holder.binding.recyclerTrWord.text = wordList.get(position).editTextTrWord

        holder.itemView.setOnClickListener{
            val action = EditFragmentDirections.actionEditFragmentToDeleteFragment(
                holder.binding.recyclerEnWord.text.toString(),
                holder.binding.recyclerTrWord.text.toString()
            )
            Navigation.findNavController(it).navigate(action)
        }
    }
}