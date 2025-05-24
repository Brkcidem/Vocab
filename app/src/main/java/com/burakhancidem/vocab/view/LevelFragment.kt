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
import com.burakhancidem.vocab.databinding.FragmentLevelBinding

class LevelFragment : Fragment() {

    private var _binding: FragmentLevelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val easyButton = binding.easyButton
        easyButton.setOnClickListener{
            val action = LevelFragmentDirections.actionLevelFragmentToModeFragment("easy")
            Navigation.findNavController(it).navigate(action)
        }

        val mediumButton = binding.mediumButton
        mediumButton.setOnClickListener{
            val action = LevelFragmentDirections.actionLevelFragmentToModeFragment("medium")
            Navigation.findNavController(it).navigate(action)
        }

        val hardButton = binding.hardButton
        hardButton.setOnClickListener{
            val action = LevelFragmentDirections.actionLevelFragmentToModeFragment("hard")
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}