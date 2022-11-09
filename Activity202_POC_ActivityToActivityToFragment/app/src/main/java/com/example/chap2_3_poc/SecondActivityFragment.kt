package com.example.chap2_3_poc

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import java.lang.RuntimeException

class SecondActivityFragment : Fragment(), View.OnClickListener {

    private lateinit var inputListener: InputListener

    private val etInput: EditText
        get() = requireView().findViewById(R.id.et_fragment_second_activity)

    private val btnSend: Button
        get() = requireView().findViewById(R.id.btn_fragment_second_activity)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is InputListener){
            inputListener = context
        } else {
            throw RuntimeException("Must implement InputListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSend.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            button -> inputListener.onSelected(etInput.text.toString().trim())
        }
    }


}