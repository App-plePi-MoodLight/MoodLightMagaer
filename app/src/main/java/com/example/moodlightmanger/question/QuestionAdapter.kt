package com.example.moodlightmanger.question

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.moodlightmanger.QuestionEditActivity
import com.example.moodlightmanger.R
import com.example.moodlightmanger.model.question.AllQuestionModel
import com.example.moodlightmanger.model.question.AllQuestionModelItem

class QuestionAdapter (val questionList : AllQuestionModel, private val onClick : (position : Int, data : AllQuestionModelItem) -> Unit)
    : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.row_question_item,
        parent, false)

        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, questionList)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    class ViewHolder(itemView: View?,val onClick: (position: Int, data: AllQuestionModelItem) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        private var questionDateTv : TextView = itemView!!.findViewById(R.id.question_date_tv)
        private var questionTitleTv : TextView = itemView!!.findViewById(R.id.question_title_tv)
        private var  questionEditBtn : AppCompatButton = itemView!!.findViewById(R.id.question_edit_btn)
        private var questionDeleteBtn : AppCompatButton = itemView!!.findViewById(R.id.question_delete_btn)
        fun bind(position: Int, questionList : AllQuestionModel) {
            questionDateTv.text = questionList[position].activatedDate
            questionTitleTv.text = questionList[position].contents

            questionEditBtn.setOnClickListener {
                var intent = Intent(itemView.context, QuestionEditActivity::class.java)
                intent.putExtra("question", questionList[position])
                itemView.context.startActivity(intent)
            }
            questionDeleteBtn.setOnClickListener {
                Log.d(TAG, "bind:클릭됨")
                onClick(position, questionList[position])
            }
        }
    }


}