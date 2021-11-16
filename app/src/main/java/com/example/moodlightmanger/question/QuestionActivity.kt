package com.example.moodlightmanger.question

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.moodlight.api.ServerClient
import com.example.moodlightmanger.QuestionCreateActivity
import com.example.moodlightmanger.R
import com.example.moodlightmanger.model.question.AllQuestionModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        ServerClient.getApiService().getQuestionAll()
            .enqueue(object : Callback<AllQuestionModel> {

                override fun onResponse(
                    call: Call<AllQuestionModel>,
                    response: Response<AllQuestionModel>,
                ) {
                    if (response.isSuccessful) {
                        val questionList : AllQuestionModel = response.body()!!


                        val questionAdapter : QuestionAdapter = QuestionAdapter(questionList)
                        findViewById<RecyclerView>(R.id.queiston_recyclerview)
                            .adapter = questionAdapter
                        questionAdapter.notifyDataSetChanged()

                        val listSize : Int = questionList.size
                        findViewById<TextView>(R.id.question_count_tv)
                            .text = "$listSize 개의 질문"
                    }
                    else {
                        Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AllQuestionModel>, t: Throwable) {
                    Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
                }

            })
        
        findViewById<ImageButton>(R.id.question_create_btn)
            .setOnClickListener {
                val intent = Intent(applicationContext, QuestionCreateActivity::class.java)
                startActivity(intent)
            }

    }
}