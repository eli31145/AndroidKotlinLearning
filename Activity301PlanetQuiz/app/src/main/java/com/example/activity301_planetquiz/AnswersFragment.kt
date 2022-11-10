package com.example.activity301_planetquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class AnswersFragment : Fragment(), View.OnClickListener {

    var questionId: Int = 0

    private val headerText: TextView?
        get() = view?.findViewById(R.id.header_text)

    private val answer: TextView?
        get() = view?.findViewById(R.id.answer)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_answers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionId = arguments?.getInt(QUESTION_ID, 0)?: 0

        val planets = listOf<Button>(
            view.findViewById(R.id.mercury),
            view.findViewById(R.id.venus),
            view.findViewById(R.id.earth),
            view.findViewById(R.id.mars),
            view.findViewById(R.id.jupiter),
            view.findViewById(R.id.saturn),
            view.findViewById(R.id.uranus),
            view.findViewById(R.id.neptune)
        )

        planets.forEach{
            it.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
       //if correct answer display text with 'correct', else display 'wrong'
        when(questionId){
            R.id.tv_largest_planet -> {
                if (v?.id == R.id.jupiter){
                    answer?.text = getString(R.string.jupiter_answer, getString(R.string.correct))
                } else {
                    answer?.text = getString(R.string.jupiter_answer, getString(R.string.wrong))
                }
            }
            R.id.tv_most_moons -> {
                if (v?.id == R.id.saturn){
                    answer?.text = getString(R.string.saturn_answer, getString(R.string.correct))
                } else {
                    answer?.text = getString(R.string.saturn_answer, getString(R.string.wrong))
                }
            }
            R.id.tv_spins_side -> {
                if (v?.id == R.id.uranus){
                    answer?.text = getString(R.string.uranus_answer, getString(R.string.correct))
                } else {
                    answer?.text = getString(R.string.uranus_answer, getString(R.string.wrong))
                }
            }
        }
    }

    companion object {
        private const val QUESTION_ID = "QUESTION_ID"

        fun newInstance(questionId: Int) = AnswersFragment().apply {
            arguments = Bundle().apply {
                putInt(QUESTION_ID, questionId)
            }
        }
    }


}