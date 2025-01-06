package com.burakhancidem.vocab.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.burakhancidem.vocab.R

class ModeFragment : Fragment() {

    private var myLevel = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLevel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enButton = view.findViewById<Button>(R.id.enButton)
        enButton.setOnClickListener{
            val action = ModeFragmentDirections.actionModeFragmentToGameFragment("english",myLevel)
            Navigation.findNavController(it).navigate(action)
        }

        val trButton = view.findViewById<Button>(R.id.trButton)
        trButton.setOnClickListener{
            val action = ModeFragmentDirections.actionModeFragmentToGameFragment("turkish",myLevel)
            Navigation.findNavController(it).navigate(action)
        }

        val randomButton = view.findViewById<Button>(R.id.randomButton)
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
}