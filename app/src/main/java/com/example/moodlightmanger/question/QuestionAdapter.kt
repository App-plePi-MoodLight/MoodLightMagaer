package com.example.moodlightmanger.question

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.moodlightmanger.QuestionEditActivity
import com.example.moodlightmanger.R
import com.example.moodlightmanger.model.QuestionModel
import com.example.moodlightmanger.model.question.AllQuestionModel
import java.util.ArrayList

class QuestionAdapter (val questionList : AllQuestionModel)
    : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.row_question_item,
        parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, questionList)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    class ViewHolder(itemView : View?) : RecyclerView.ViewHolder(itemView!!) {
        private var questionDateTv : TextView = itemView!!.findViewById(R.id.question_date_tv)
        private var questionTitleTv : TextView = itemView!!.findViewById(R.id.question_title_tv)
        private var  questionEditBtn : AppCompatButton = itemView!!.findViewById(R.id.question_edit_btn)
        private var questionDeleteBtn : AppCompatButton = itemView!!.findViewById(R.id.question_delete_btn)

        fun bind(position: Int, questionList : AllQuestionModel) {
            questionDateTv.text = questionList[position].activatedDate
            questionTitleTv.text = questionList[position].contents

            questionEditBtn.setOnClickListener {
                var intent = Intent(itemView.context, QuestionEditActivity::class.java)
                intent.putExtra("id", questionList[position].id)
                intent.putExtra("mood", questionList[position].mood)
                intent.putExtra("date", questionList[position].activatedDate)
                itemView.context.startActivity(intent)
            }
            questionDeleteBtn.setOnClickListener {
                var intent = Intent(itemView.context, QuestionEditActivity::class.java)
                intent.putExtra("id", questionList[position].id)
                itemView.context.startActivity(intent)
            }
        }
    }
}