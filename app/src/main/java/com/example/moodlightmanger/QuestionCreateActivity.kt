package com.example.moodlightmanger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.moodlight.api.ServerClient
import com.example.moodlightmanger.model.QuestionCreateModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_create)

        findViewById<AppCompatButton>(R.id.create_btn)
            .setOnClickListener {
                createQuestion(findViewById<EditText>(R.id.date_etv).text.toString(),
                    findViewById<EditText>(R.id.content_etv).text.toString())
            }

    }

    private fun createQuestion(date : String, contents : String) {

        CoroutineScope(Dispatchers.IO).launch {
            ServerClient.getApiService().createQuestion(
                QuestionCreateModel("angry",date,
                    contents)
            ).enqueue(object : Callback<QuestionCreateModel> {

                override fun onResponse(
                    call: Call<QuestionCreateModel>,
                    response: Response<QuestionCreateModel>,
                ) {
                    if (!response.isSuccessful) {

                        Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<QuestionCreateModel>, t: Throwable) {
                    t.printStackTrace()
                }

            })

            ServerClient.getApiService().createQuestion(
                QuestionCreateModel("sad",date,
                    contents)
            ).enqueue(object : Callback<QuestionCreateModel> {

                override fun onResponse(
                    call: Call<QuestionCreateModel>,
                    response: Response<QuestionCreateModel>,
                ) {
                    if (!response.isSuccessful) {

                        Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<QuestionCreateModel>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }

        ServerClient.getApiService().createQuestion(
            QuestionCreateModel("happy",date,
                contents)
        ).enqueue(object : Callback<QuestionCreateModel> {

            override fun onResponse(
                call: Call<QuestionCreateModel>,
                response: Response<QuestionCreateModel>,
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else {
                    Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<QuestionCreateModel>, t: Throwable) {
                t.printStackTrace()
            }

        })


    }
}