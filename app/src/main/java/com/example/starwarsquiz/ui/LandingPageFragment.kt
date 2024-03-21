package com.example.starwarsquiz.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.CharacterDetails
import com.example.starwarsquiz.data.QuestionContents
import com.example.starwarsquiz.data.SWAPICharacter
import kotlin.random.Random

class LandingPageFragment : Fragment(R.layout.fragment_landing_page){
    private lateinit var startButton: Button
    private lateinit var historyButton: Button

    private val characterListViewModel: SWAPICharacterViewModel by viewModels()
    private val characterDetailsViewModel: SWAPICharacterDetailsViewModel by viewModels()

    private var characterList: List<SWAPICharacter>? = null
    private var characterDetails: CharacterDetails? = null
    private var listSize = 50
    private val randomNumber = Random.nextInt(listSize, 17) - 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("LandingPageFragment", "Random Num: ${randomNumber}")

        characterListViewModel.loadSWAPICharacters(1, listSize)
        characterDetailsViewModel.loadSWAPICharactersDetails(randomNumber)

        startButton = view.findViewById(R.id.start_button)
        historyButton = view.findViewById(R.id.button_score_history)

        characterListViewModel.characterResults.observe(viewLifecycleOwner) { CharacterList ->
            if (CharacterList != null) {
                characterList = CharacterList
            } else {
                Log.d("MCFragment", "character list is null")
            }
        }

        characterDetailsViewModel.characterDetails.observe(viewLifecycleOwner) { character ->
            if (character != null) {
                characterDetails = character
            } else {
                Log.d("MCFragment", "character details is null")
            }
        }

        // navigate to first question on start btn click
        startButton.setOnClickListener {
            val newArgs = QuestionContents(
                1,
                0,
                "What is the Homeworld of ${characterList?.get(randomNumber - 1)?.name}",
                "${characterDetails?.homeworldUrl}",
                listOf("${characterDetails?.homeworldUrl}", "ANSWER 2", "ANSWER 3", "ANSWER 4")
            )
            val action = LandingPageFragmentDirections.navigateToQuizQuestionMc(newArgs)
            findNavController().navigate(action)
        }

        historyButton.setOnClickListener {
            val action = LandingPageFragmentDirections.navigateToScoreHistory()
            findNavController().navigate(action)
        }
    }
}
