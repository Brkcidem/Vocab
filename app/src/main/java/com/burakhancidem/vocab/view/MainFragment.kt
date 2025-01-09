package com.burakhancidem.vocab.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.burakhancidem.vocab.R
import com.burakhancidem.vocab.databinding.FragmentMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

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
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.signout){
            auth.signOut()
            findNavController().navigate(R.id.action_mainFragment_to_singFragment)
            true
        }else if(item.itemId == R.id.edit_words){
            findNavController().navigate(R.id.action_mainFragment_to_editFragment)
            true
        }else
            super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playButton = view.findViewById<Button>(R.id.playButton)
        playButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_levelFragment)
        }
    }
}