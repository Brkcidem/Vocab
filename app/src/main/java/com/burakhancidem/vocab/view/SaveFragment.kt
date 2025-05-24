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
import com.burakhancidem.vocab.databinding.FragmentSaveBinding
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class SaveFragment : Fragment() {

    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        firestore = Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (auth.currentUser != null) {
            val saveButton = binding.saveButton
            saveButton.setOnClickListener {

                val enWord = binding.editTextEnWord.text.toString()
                val trWord = binding.editTextTrWord.text.toString()

                // Firestore'da "editTextEnWord" veya "editTextTrWord" değerlerinden biri var mı kontrol et
                firestore.collection("Words")
                    .whereEqualTo("editTextEnWord", enWord)
                    .get()
                    .addOnSuccessListener { enQuerySnapshot ->
                        if (!enQuerySnapshot.isEmpty) {
                            Toast.makeText(requireActivity(), "The English word already exists", Toast.LENGTH_LONG).show()
                        } else {
                            firestore.collection("Words")
                                .whereEqualTo("editTextTrWord", trWord)
                                .get()
                                .addOnSuccessListener { trQuerySnapshot ->
                                    if (!trQuerySnapshot.isEmpty) {
                                        // Eğer editTextTrWord zaten varsa, uyarı göster ve kayıta izin verme
                                        Toast.makeText(requireActivity(), "The Turkish word already exists", Toast.LENGTH_LONG).show()
                                    } else {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}