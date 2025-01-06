package com.burakhancidem.vocab.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.burakhancidem.vocab.R

class LevelFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun Fragment.handleBackPressWithWarning(onBackPressedAction: () -> Unit) {
            requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Navigation.findNavController(view!!).navigate(R.id.action_levelFragment_to_mainFragment)
                }
            })
        }
        handleBackPressWithWarning {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_level, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val easyButton = view.findViewById<Button>(R.id.easyButton)
        easyButton.setOnClickListener{
            val action = LevelFragmentDirections.actionLevelFragmentToModeFragment("easy")
            Navigation.findNavController(it).navigate(action)
        }

        val mediumButton = view.findViewById<Button>(R.id.mediumButton)
        mediumButton.setOnClickListener{
            val action = LevelFragmentDirections.actionLevelFragmentToModeFragment("medium")
            Navigation.findNavController(it).navigate(action)
        }

        val hardButton = view.findViewById<Button>(R.id.hardButton)
        hardButton.setOnClickListener{
            val action = LevelFragmentDirections.actionLevelFragmentToModeFragment("hard")
            Navigation.findNavController(it).navigate(action)
        }
    }
}