package com.example.sumnote.ui.Quiz

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sumnote.databinding.FragmentQuizBinding
import com.example.sumnote.databinding.FragmentQuizViewerBinding

//data class Question(val quest: String, val answerList: ArrayList<String>, val answerNum : Int, val explanation : String)
//quest : 질문
//answerList : 정답 리스트(1번부터 4번까지)
//answerNum : 정답 번호(사용자 선택과 비교)

data class Quiz(
    val quest: String,
    val answerList: ArrayList<String>,
    val answerNum: Int,
    val explanation: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(quest)
        parcel.writeList(answerList)
        parcel.writeInt(answerNum)
        parcel.writeString(explanation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Quiz> {
        override fun createFromParcel(parcel: Parcel): Quiz {
            return Quiz(parcel)
        }

        override fun newArray(size: Int): Array<Quiz?> {
            return arrayOfNulls(size)
        }
    }
}

class QuizFragment : Fragment() {

    private lateinit var quiz: Quiz //프래그먼트 생성시점에 객체 할당

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    //번들로 부터 quiz클래스 얻어오기(해당 페이지에 맞는)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quiz = it.getParcelable(ARG_QUESTION)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)



        return binding.root
    }

    companion object {
        private const val ARG_QUESTION = "question"
        //번들로 객체를 전달하기 위해 question객체를 Parcelable객체로 변환
        fun newInstance(question: Quiz) = QuizFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_QUESTION, question)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}