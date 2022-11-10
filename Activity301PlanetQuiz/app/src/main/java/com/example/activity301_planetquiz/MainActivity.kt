package com.example.activity301_planetquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView

interface SelectionListener{
    fun onSelected(id: Int)
}

class MainActivity : AppCompatActivity(), SelectionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //if there is no prior saved instance state, display question fragment
        if (savedInstanceState == null){
            findViewById<FragmentContainerView>(R.id.fragment_container)?.let {
                frameLayout ->
                val questionFragment = QuestionsFragment()
                supportFragmentManager.beginTransaction().add(frameLayout.id, questionFragment).commit()
            }
        }
    }

    //need to implement SelectionListener here as onSelected() can only access fragment_container in
    //linked activity_main.xml
    //used for QuestionsFragment to communicate back to activity when user selects a question
    override fun onSelected(id: Int){
        findViewById<FragmentContainerView>(R.id.fragment_container)?.let { framelayout ->
            val answersFragment = AnswersFragment.newInstance(id)

            supportFragmentManager.beginTransaction().replace(framelayout.id, answersFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}