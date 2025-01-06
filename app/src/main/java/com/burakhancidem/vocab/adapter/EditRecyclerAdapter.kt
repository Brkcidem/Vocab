package com.burakhancidem.vocab.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.burakhancidem.vocab.databinding.RecyclerRowBinding
import com.burakhancidem.vocab.model.Word
import com.burakhancidem.vocab.view.DeleteFragment
import com.burakhancidem.vocab.view.EditFragmentDirections
import com.burakhancidem.vocab.view.GameFragmentDirections
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class EditRecyclerAdapter(private val wordList: ArrayList<Word>): RecyclerView.Adapter<EditRecyclerAdapter.WordHolder>() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

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
        auth = Firebase.auth
        firestore = Firebase.firestore

        holder.binding.recyclerEnWord.text = wordList.get(position).editTextEnWord
        holder.binding.recyclerTrWord.text = wordList.get(position).editTextTrWord

        holder.itemView.setOnClickListener{
            val action = EditFragmentDirections.actionEditFragmentToDeleteFragment(holder.binding.recyclerEnWord.text.toString(),holder.binding.recyclerTrWord.text.toString())
            Navigation.findNavController(it).navigate(action)
        }
    }
}