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
import androidx.activity.OnBackPressedCallback

class ScoreFragment : Fragment() {

    private var myScore = 0
    private var myBestScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playAgainButton = view.findViewById<Button>(R.id.playAgainButton)
        playAgainButton.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_scoreFragment_to_levelFragment)
        }

        val goToMainMenuButton = view.findViewById<Button>(R.id.goToMainMenuButton)
        goToMainMenuButton.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_scoreFragment_to_mainFragment)
        }

        arguments?.let {
            myScore = ScoreFragmentArgs.fromBundle(it).reachedScore
            val scoreTextView = view.findViewById<TextView>(R.id.scoreTextView)
            scoreTextView.text = "Your Score: ${myScore.toString()}"

            myBestScore = ScoreFragmentArgs.fromBundle(it).reachedBestScore
            val bestScoreTextView = view.findViewById<TextView>(R.id.bestScoreTextView)
            bestScoreTextView.text = "Best Score: ${myBestScore.toString()}"

        }
    }
}