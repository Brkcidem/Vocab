package com.burakhancidem.vocab.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.burakhancidem.vocab.R
import com.burakhancidem.vocab.databinding.FragmentModeBinding

class ModeFragment : Fragment() {

    private var _binding: FragmentModeBinding? = null
    private val binding get() = _binding!!

    private var myLevel = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLevel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enButton = binding.enButton
        enButton.setOnClickListener{
            val action = ModeFragmentDirections.actionModeFragmentToGameFragment("english",myLevel)
            Navigation.findNavController(it).navigate(action)
        }

        val trButton = binding.trButton
        trButton.setOnClickListener{
            val action = ModeFragmentDirections.actionModeFragmentToGameFragment("turkish",myLevel)
            Navigation.findNavController(it).navigate(action)
        }

        val randomButton = binding.randomButton
        randomButton.setOnClickListener{
            val action = ModeFragmentDirections.actionModeFragmentToGameFragment("random",myLevel)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun getLevel(){
        arguments?.let {
            myLevel = ModeFragmentArgs.fromBundle(it).selectedLevel
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}