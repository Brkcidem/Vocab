package com.burakhancidem.vocab.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.burakhancidem.vocab.R
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class SaveFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_save, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (auth.currentUser != null) {
            val saveButton = view.findViewById<Button>(R.id.saveButton)
            saveButton.setOnClickListener {

                val editTextEnWord = view.findViewById<EditText>(R.id.editTextEnWord)
                val editTextTrWord = view.findViewById<EditText>(R.id.editTextTrWord)
                val enWord = editTextEnWord.text.toString()
                val trWord = editTextTrWord.text.toString()

                // Firestore'da "editTextEnWord" veya "editTextTrWord" değerlerinden biri var mı kontrol et
                firestore.collection("Words")
                    .whereEqualTo("editTextEnWord", enWord)
                    .get()
                    .addOnSuccessListener { enQuerySnapshot ->
                        if (!enQuerySnapshot.isEmpty) {
                            // Eğer editTextEnWord zaten varsa, uyarı göster ve kayıta izin verme
                            Toast.makeText(requireActivity(), "The English word already exists", Toast.LENGTH_LONG).show()
                        } else {
                            // editTextTrWord için de aynı kontrolü yap
                            firestore.collection("Words")
                                .whereEqualTo("editTextTrWord", trWord)
                                .get()
                                .addOnSuccessListener { trQuerySnapshot ->
                                    if (!trQuerySnapshot.isEmpty) {
                                        // Eğer editTextTrWord zaten varsa, uyarı göster ve kayıta izin verme
                                        Toast.makeText(requireActivity(), "The Turkish word already exists", Toast.LENGTH_LONG).show()
                                    } else {
                                        // Aynı kelimeler yoksa kaydet
                                        val wordMap = hashMapOf<String, Any>(
                                            "editTextEnWord" to enWord,
                                            "editTextTrWord" to trWord,
                                            "editTextEmailAddress" to auth.currentUser!!.email!!,
                                            "date" to Timestamp.now()
                                        )

                                        firestore.collection("Words").add(wordMap)
                                            .addOnSuccessListener {
                                                Navigation.findNavController(view).navigate(R.id.action_saveFragment_to_editFragment)
                                                Toast.makeText(requireActivity(), "Saved successfully", Toast.LENGTH_LONG).show()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
                                            }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(requireActivity(), exception.localizedMessage, Toast.LENGTH_LONG).show()
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(requireActivity(), exception.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            }
        }
    }

}