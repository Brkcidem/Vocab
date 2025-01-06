package com.burakhancidem.vocab.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.clearFragmentResult
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.burakhancidem.vocab.R
import com.burakhancidem.vocab.databinding.FragmentSingBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
class SingFragment : Fragment() {

    lateinit var binding: FragmentSingBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        val currentUser = auth.currentUser

        if (currentUser != null){
            val navController = findNavController()
            navController.navigate(R.id.action_singFragment_to_mainFragment)
        }

        fun Fragment.handleBackPressWithWarning(onBackPressedAction: () -> Unit) {
            requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressedAction()
                }
            })
        }

        handleBackPressWithWarning {
            Toast.makeText(requireContext(), "Back press is disabled", Toast.LENGTH_SHORT).show()
        }
    }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_sing, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val signUpButton = view.findViewById<Button>(R.id.signUpButton)
            signUpButton.setOnClickListener {

                val email = view.findViewById<EditText>(R.id.editTextEmailAddress)
                val password = view.findViewById<EditText>(R.id.editTextPassword)

                if (email.text.toString().equals("") || password.text.toString().equals("")) {
                    Toast.makeText(
                        requireActivity(),
                        "Enter email and password!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    auth.createUserWithEmailAndPassword(
                        email.text.toString(),
                        password.text.toString()
                    )
                        .addOnSuccessListener {
                            Navigation.findNavController(view)
                                .navigate(R.id.action_singFragment_to_mainFragment)
                            Toast.makeText(
                                requireActivity(),
                                "Account has been created successfully",
                                Toast.LENGTH_LONG
                            ).show()
                        }.addOnFailureListener {
                            Toast.makeText(
                                requireActivity(),
                                it.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }
            }

            val signInButton = view.findViewById<Button>(R.id.signInButton)
            signInButton.setOnClickListener {
                val email = view.findViewById<EditText>(R.id.editTextEmailAddress)
                val password = view.findViewById<EditText>(R.id.editTextPassword)

                if (email.text.toString().equals("") || password.text.toString().equals("")) {
                    Toast.makeText(
                        requireActivity(),
                        "Enter email and password!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                        .addOnSuccessListener {
                            Navigation.findNavController(view)
                                .navigate(R.id.action_singFragment_to_mainFragment)
                            Toast.makeText(
                                requireActivity(),
                                "Signed in successfully",
                                Toast.LENGTH_LONG
                            ).show()
                        }.addOnFailureListener {
                            Toast.makeText(
                                requireActivity(),
                                it.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }
            }

        }
}
