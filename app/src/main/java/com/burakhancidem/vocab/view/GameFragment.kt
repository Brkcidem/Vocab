package com.burakhancidem.vocab.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.burakhancidem.vocab.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlin.random.Random

class GameFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var myMode = ""
    private var myLevels = ""
    private var score : Int = 0
    private var bestScoreTextView: Int = 0
    private var heart : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        firestore = Firebase.firestore
        getLevels()
        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    ///////MENU////////
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.game_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.exit) {
            findNavController().navigate(R.id.action_gameFragment_to_mainFragment)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
    ///////////////////

    private fun checkAndUpdateBestScore() {

        val userEmail = auth.currentUser?.email
        if (userEmail != null && myLevels == "easy") {
            // Kullanıcının en yüksek skorunu al
            val userDocRef = firestore.collection("Users").document(userEmail)
            userDocRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val currentBestScore = documentSnapshot.getLong("bestScoreEasy") ?: 0
                    // Eğer yeni skor daha büyükse, en yüksek skoru güncelle
                    if (score > currentBestScore) {
                        userDocRef.update("bestScoreEasy", score).addOnSuccessListener {
                            Toast.makeText(requireContext(), "New best score saved!", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { exception ->
                            Toast.makeText(requireContext(), "Failed to save best score: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                        bestScoreTextView = score
                    }else{
                        bestScoreTextView = currentBestScore.toInt()
                    }


                } else {
                    // Eğer kullanıcı belgesi yoksa, yeni bir belge oluştur ve en yüksek skoru kaydet
                    val userData = hashMapOf(
                        "bestScoreEasy" to score
                    )
                    userDocRef.set(userData).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Best score saved!", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener { exception ->
                        Toast.makeText(requireContext(), "Failed to save best score: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }

            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to retrieve best score: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }

        if (userEmail != null && myLevels == "medium") {
            // Kullanıcının en yüksek skorunu al
            val userDocRef = firestore.collection("Users").document(userEmail)
            userDocRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val currentBestScore = documentSnapshot.getLong("bestScoreMedium") ?: 0
                    // Eğer yeni skor daha büyükse, en yüksek skoru güncelle
                    if (score > currentBestScore) {
                        userDocRef.update("bestScoreMedium", score).addOnSuccessListener {
                            Toast.makeText(requireContext(), "New best score saved!", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { exception ->
                            Toast.makeText(requireContext(), "Failed to save best score: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                        bestScoreTextView = score
                    }else{
                        bestScoreTextView = currentBestScore.toInt()
                    }

                } else {
                    // Eğer kullanıcı belgesi yoksa, yeni bir belge oluştur ve en yüksek skoru kaydet
                    val userData = hashMapOf(
                        "bestScoreMedium" to score
                    )
                    userDocRef.set(userData).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Best score saved!", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener { exception ->
                        Toast.makeText(requireContext(), "Failed to save best score: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }

            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to retrieve best score: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }

        if (userEmail != null && myLevels == "hard") {
            // Kullanıcının en yüksek skorunu al
            val userDocRef = firestore.collection("Users").document(userEmail)
            userDocRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val currentBestScore = documentSnapshot.getLong("bestScoreHard") ?: 0
                    // Eğer yeni skor daha büyükse, en yüksek skoru güncelle
                    if (score > currentBestScore) {
                        userDocRef.update("bestScoreHard", score).addOnSuccessListener {
                            Toast.makeText(requireContext(), "New best score saved!", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { exception ->
                            Toast.makeText(requireContext(), "Failed to save best score: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                        bestScoreTextView = score
                    }else{
                        bestScoreTextView = currentBestScore.toInt()
                    }


                } else {
                    // Eğer kullanıcı belgesi yoksa, yeni bir belge oluştur ve en yüksek skoru kaydet
                    val userData = hashMapOf(
                        "bestScoreHard" to score
                    )
                    userDocRef.set(userData).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Best score saved!", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener { exception ->
                        Toast.makeText(requireContext(), "Failed to save best score: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }

            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to retrieve best score: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun getLevels(){
        arguments?.let {
            myLevels = GameFragmentArgs.fromBundle(it).selectedLevels
            if (myLevels == "medium") {
                heart = 3
            }else if(myLevels == "easy"){
                heart = 10000
            }else{
                heart = 1
            }
        }
    }

    fun getHeart(){
        val imageView1 = view?.findViewById<ImageView>(R.id.imageView1)
        val imageView2 = view?.findViewById<ImageView>(R.id.imageView2)
        val imageView3 = view?.findViewById<ImageView>(R.id.imageView3)

        if(heart == 3){
            imageView1?.visibility = View.VISIBLE
            imageView2?.visibility = View.VISIBLE
            imageView3?.visibility = View.VISIBLE
        }else if(heart == 2){
            imageView1?.visibility = View.INVISIBLE
            imageView2?.visibility = View.VISIBLE
            imageView3?.visibility = View.VISIBLE
        }else if(heart == 1){
            imageView1?.visibility = View.INVISIBLE
            imageView2?.visibility = View.INVISIBLE
            imageView3?.visibility = View.VISIBLE
        }else{
            imageView1?.visibility = View.INVISIBLE
            imageView2?.visibility = View.INVISIBLE
            imageView3?.visibility = View.INVISIBLE
        }
    }

    fun getData() {
        fun enMode(){
            firestore.collection("Words").whereEqualTo("editTextEmailAddress",auth.currentUser!!.email!!).get().addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val documents = result.documents
                    val randomDocument = documents.random()
                    val editTextEnWord = randomDocument.getString("editTextEnWord")
                    val editTextTrWord = randomDocument.getString("editTextTrWord")
                    val textViewWord = view?.findViewById<TextView>(R.id.textViewWord)
                    val editTextWord = view?.findViewById<TextView>(R.id.editTextWord)

                    textViewWord?.text = editTextEnWord
                    editTextWord?.hint = "Turkish meaning"

                    getHeart()

                    val guessButton = view?.findViewById<Button>(R.id.guessButton)
                    guessButton?.setOnClickListener {

                        if (editTextWord?.text.toString() == editTextTrWord) {
                            score = score + 1
                            Toast.makeText(requireActivity(), "Correct", Toast.LENGTH_SHORT)
                                .show()
                            getData()
                            editTextWord?.text = ""
                        } else {
                            if (editTextWord?.text.toString() == "") {
                                Toast.makeText(
                                    requireActivity(),
                                    "Fill in the blank!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                if(myLevels == "medium" || myLevels == "hard"){
                                    heart --
                                    getHeart()
                                }else{}
                                if (heart == 0){
                                    Toast.makeText(requireActivity(), editTextTrWord, Toast.LENGTH_LONG)
                                        .show()
                                        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score,bestScoreTextView)
                                        Navigation.findNavController(it).navigate(action)
                                }else{
                                    Toast.makeText(
                                        requireActivity(),
                                        "Try Again!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            editTextWord?.text = ""
                        }
                    }

                    val skipButton = view?.findViewById<Button>(R.id.skipButton)
                    skipButton?.setOnClickListener {
                        if(myLevels == "medium" || myLevels == "hard"){
                            heart --
                        }else{ }
                        if (heart == 0){
                            Toast.makeText(requireActivity(), editTextTrWord, Toast.LENGTH_LONG)
                                .show()
                            val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score,bestScoreTextView)
                            Navigation.findNavController(it).navigate(action)
                        }else{
                            Toast.makeText(requireActivity(), editTextTrWord, Toast.LENGTH_LONG)
                                .show()
                            getData()
                            editTextWord?.text = ""
                        }
                    }

                    val finishButton = view?.findViewById<Button>(R.id.finishButton)
                    finishButton?.setOnClickListener{
                        Toast.makeText(requireActivity(), editTextTrWord, Toast.LENGTH_LONG)
                            .show()
                        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score,bestScoreTextView)
                        Navigation.findNavController(it).navigate(action)
                    }

                }
            }.addOnFailureListener { exception ->
                println("Error getting documents: ${exception.localizedMessage}")
            }
        }

        fun trMode(){
            firestore.collection("Words").whereEqualTo("editTextEmailAddress",auth.currentUser!!.email!!).get().addOnSuccessListener { result ->
                if (!result.isEmpty) {

                    val documents = result.documents
                    val randomDocument = documents.random()
                    val editTextEnWord = randomDocument.getString("editTextEnWord")
                    val editTextTrWord = randomDocument.getString("editTextTrWord")
                    val textViewWord = view?.findViewById<TextView>(R.id.textViewWord)
                    val editTextWord = view?.findViewById<TextView>(R.id.editTextWord)

                    textViewWord?.text = editTextTrWord
                    editTextWord?.hint = "English meaning"

                    getHeart()

                    val guessButton = view?.findViewById<Button>(R.id.guessButton)
                    guessButton?.setOnClickListener {
                        if (editTextWord?.text.toString() == editTextEnWord) {
                            score = score + 1
                            Toast.makeText(requireActivity(), "Correct", Toast.LENGTH_SHORT)
                                .show()
                            getData()
                            editTextWord?.text = ""
                        } else {
                            if (editTextWord?.text.toString() == "") {
                                Toast.makeText(
                                    requireActivity(),
                                    "Fill in the blank!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                if(myLevels == "medium" || myLevels == "hard"){
                                    heart --
                                    getHeart()
                                }else{ }
                                if (heart == 0){
                                    Toast.makeText(requireActivity(), editTextEnWord, Toast.LENGTH_LONG)
                                        .show()
                                    val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score,bestScoreTextView)
                                    Navigation.findNavController(it).navigate(action)
                                }else{
                                    Toast.makeText(
                                        requireActivity(),
                                        "Try Again!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            editTextWord?.text = ""
                        }
                    }

                    val skipButton = view?.findViewById<Button>(R.id.skipButton)
                    skipButton?.setOnClickListener {
                        if(myLevels == "medium" || myLevels == "hard"){
                            heart --
                        }else{ }
                        if (heart == 0){
                            Toast.makeText(requireActivity(), editTextEnWord, Toast.LENGTH_LONG)
                                .show()
                            val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score,bestScoreTextView)
                            Navigation.findNavController(it).navigate(action)
                        }else{
                            Toast.makeText(requireActivity(), editTextEnWord, Toast.LENGTH_LONG)
                                .show()
                            getData()
                            editTextWord?.text = ""
                        }
                    }

                    val finishButton = view?.findViewById<Button>(R.id.finishButton)
                    finishButton?.setOnClickListener{
                        Toast.makeText(requireActivity(), editTextEnWord, Toast.LENGTH_LONG)
                            .show()
                        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score,bestScoreTextView)
                        Navigation.findNavController(it).navigate(action)
                    }

                }
            }.addOnFailureListener { exception ->
                println("Error getting documents: ${exception.localizedMessage}")
            }
        }

        fun ranMode(){
            var ran = Random.nextInt(0, 2)

            if(ran == 0){
                enMode()
            }else{
                trMode()
            }
        }

        arguments?.let {
            myMode = GameFragmentArgs.fromBundle(it).selectedMode
            if (myMode == "english") {
                enMode()
            }else if(myMode == "turkish"){
                trMode()
            }else{
                ranMode()
            }
        }
        checkAndUpdateBestScore()
    }

}