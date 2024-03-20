package com.example.starwarsquiz.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.QuestionContents

class QuizQuestionMCFragment : Fragment(R.layout.fragment_quiz_question_mc){
    private val args: QuizQuestionMCFragmentArgs by navArgs()

    // declare necessary view models here
    private val quizScoreViewModel: QuizScoreViewModel by viewModels()

    // stores list of possible answers to this question
    private val adapter = MCAnswerListAdapter()

    private lateinit var questionNumTV: TextView
    private lateinit var currentScoreTV: TextView
    private lateinit var questionTV: TextView
    private lateinit var answerListRV: RecyclerView
    private lateinit var submitButton: Button
    private lateinit var nextButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionNumTV = view.findViewById(R.id.tv_quiz_question_num)
        currentScoreTV = view.findViewById(R.id.tv_quiz_current_score)
        questionTV = view.findViewById(R.id.tv_quiz_question)
        answerListRV = view.findViewById(R.id.rv_quiz_question_answers)
        submitButton = view.findViewById(R.id.button_submit)
        nextButton = view.findViewById(R.id.button_next)

        answerListRV.adapter = adapter
        answerListRV.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        /*
            perform logic below

            eg. when submit is clicked, create snackbar indicating if answer was correct,
            then turn button invisible, and set 'next' button to visible to navigate to next question
            (or results screen if last question)

            since answer choices are in MC format, create a toggle function that changes the appearance of
            a clicked answer in the RV and compare it to the correct answer upon clicking submit
            (perform error handling when no answer is selected)
         */

        // If this is not the final question, show the next button
        if (args.questionContents.quizNumber < 10) {
            Log.d("QuizQuestionMCFragment", "Next button visible")
            nextButton.visibility = View.VISIBLE
            submitButton.visibility = View.INVISIBLE
        }

        // Set question number
        questionNumTV.text = args.questionContents.quizNumber.toString()

        // Set question
        questionTV.text = args.questionContents.question

        // Set current score
        currentScoreTV.text = args.questionContents.currentScore.toString()

        // Set answer list
        adapter.updateAnswerList(args.questionContents.answerChoices)


        // Submit button goes to results screen
        submitButton.setOnClickListener {
            val score = if (true) { // TODO: Replace with actual answer comparison
                // If answer is correct, increment score
                args.questionContents.currentScore + 1
            } else {
                // If answer is incorrect, do not increment score
                args.questionContents.currentScore
            }
            val action = QuizQuestionMCFragmentDirections.navigateToQuizResults(score)
            findNavController().navigate(action)
        }

        // Next button goes to next question
        nextButton.setOnClickListener {
            val nextScore = if (true) { // TODO: Replace with actual answer comparison
                // If answer is correct, increment score
                args.questionContents.currentScore + 1
            } else {
                // If answer is incorrect, do not increment score
                args.questionContents.currentScore
            }

            val newArgs = QuestionContents(
                args.questionContents.quizNumber + 1,
                nextScore,
                "REPLACE ME WITH AN ACTUAL QUESTION",
                "ANSWER 1",
                listOf("ANSWER 1", "ANSWER 2", "ANSWER 3", "ANSWER 4")
            )

            val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
            findNavController().navigate(action)
        }


    }
}