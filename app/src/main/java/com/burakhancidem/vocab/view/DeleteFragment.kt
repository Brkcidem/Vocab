package com.burakhancidem.vocab.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.burakhancidem.vocab.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class DeleteFragment : Fragment() {

    private lateinit var firestore: FirebaseFirestore

    private var myEnWord = ""
    private var myTrWord = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firestore = Firebase.firestore

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            myEnWord = DeleteFragmentArgs.fromBundle(it).selectedEnWord
            myTrWord = DeleteFragmentArgs.fromBundle(it).selectedTrWord

            val editTextTextEn = view.findViewById<TextView>(R.id.editTextTextEn)
            val editTextTextTr = view.findViewById<TextView>(R.id.editTextTextTr)

            editTextTextEn.text = myEnWord
            editTextTextTr.text = myTrWord

            val deleteButton = view.findViewById<Button>(R.id.deleteButton)

            deleteButton.setOnClickListener{

                if (editTextTextEn.text.isNotEmpty() && editTextTextTr.text.isNotEmpty()){
                    firestore.collection("Words")
                        .whereEqualTo("editTextEnWord",editTextTextEn.text.toString())
                        .whereEqualTo("editTextTrWord",editTextTextTr.text.toString())
                        .get()
                    .addOnSuccessListener {
                        for (document in it){
                            firestore.collection("Words").document(document.id).delete().addOnSuccessListener {
                                Toast.makeText(requireActivity(),"Words deleted",Toast.LENGTH_SHORT).show()
                                Navigation.findNavController(view).navigate(R.id.action_deleteFragment_to_editFragment)
                            }
                        }
                    }
                }else{
                    Toast.makeText(requireActivity(),"Type words!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}