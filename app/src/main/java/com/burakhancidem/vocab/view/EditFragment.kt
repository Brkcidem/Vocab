package com.burakhancidem.vocab.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakhancidem.vocab.R
import com.burakhancidem.vocab.adapter.EditRecyclerAdapter
import com.burakhancidem.vocab.databinding.FragmentEditBinding
import com.burakhancidem.vocab.model.Word
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import androidx.activity.OnBackPressedCallback

class EditFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var wordArrayList: ArrayList<Word>
    private lateinit var editAdapter: EditRecyclerAdapter
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        db = Firebase.firestore
        wordArrayList = ArrayList()
        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(layoutInflater,container,false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        editAdapter = EditRecyclerAdapter(wordArrayList)
        binding.recyclerView.adapter = editAdapter
        return binding.root
    }

    ///////MENU////////
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.add_words){
            findNavController().navigate(R.id.action_editFragment_to_saveFragment)
            true
        }else
            super.onOptionsItemSelected(item)
    }
    ///////////////////

    fun getData() {
        db.collection("Words").whereEqualTo("editTextEmailAddress",auth.currentUser!!.email!!).orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null) {
            } else {
                if (value != null) {
                    if (!value.isEmpty) {
                        val documents = value.documents
                        wordArrayList.clear()
                        for (document in documents) {
                            val editTextEnWord = document.get("editTextEnWord") as String
                            val editTextTrWord = document.get("editTextTrWord") as String
                            val word = Word(editTextEnWord, editTextTrWord)
                            wordArrayList.add(word)
                        }
                        editAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}