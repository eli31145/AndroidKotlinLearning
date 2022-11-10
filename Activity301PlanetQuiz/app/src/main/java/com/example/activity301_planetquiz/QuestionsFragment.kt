package com.example.activity301_planetquiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class QuestionsFragment : Fragment(), View.OnClickListener {

    private lateinit var selectionListener: SelectionListener

    //if don't have onAttach() method, app crash when choosing question since selectionListener not initialized
    //stating that activity implements SelectionListener interface so it can be notiified
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SelectionListener) {
            selectionListener = context
        } else {
            throw RuntimeException("Must implement AnswersListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questions = listOf<Button>(
            view.findViewById(R.id.tv_largest_planet),
            view.findViewById(R.id.tv_most_moons),
            view.findViewById(R.id.tv_spins_side)
        )

        questions.forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        //depending on which question is clicked, open answers fragment
        v?.let {
            question -> selectionListener.onSelected(question.id)
        }
    }

}