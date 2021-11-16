package com.example.moodlightmanger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.moodlight.api.ServerClient
import com.example.moodlightmanger.databinding.ActivityQuestionEditBinding
import com.example.moodlightmanger.model.EditQuestionModel
import com.example.moodlightmanger.model.SuccessResponseModel
import com.example.moodlightmanger.model.question.AllQuestionModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionEditActivity : AppCompatActivity() {

    lateinit var binding : ActivityQuestionEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_question_edit)

        val questionModel : AllQuestionModelItem = intent.getSerializableExtra("question") as AllQuestionModelItem

        binding.date = questionModel.activatedDate
        binding.contentEtv.hint = questionModel.contents

        binding.editBtn.setOnClickListener {
            if (binding.contentEtv.text.toString().equals("")) {
                Toast.makeText(applicationContext, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()

            }
            else {
                val editQuestionModel: EditQuestionModel = EditQuestionModel(questionModel.id,
                    questionModel.mood,
                    questionModel.activatedDate,
                    binding.contentEtv.text.toString())
                ServerClient.getApiService().editQuestion(editQuestionModel)
                    .enqueue(object : Callback<SuccessResponseModel> {

                        override fun onResponse(
                            call: Call<SuccessResponseModel>,
                            response: Response<SuccessResponseModel>,
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(applicationContext,
                                    "질문이 성공적으로 수정되었습니다.",
                                    Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(applicationContext,
                                    "error : ${response.code()}",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<SuccessResponseModel>, t: Throwable) {
                            t.printStackTrace()
                        }

                    })
            }
        }
    }
}