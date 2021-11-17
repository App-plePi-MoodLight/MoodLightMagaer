package com.example.moodlightmanger.question

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.moodlight.api.ServerClient
import com.example.moodlightmanger.QuestionCreateActivity
import com.example.moodlightmanger.R
import com.example.moodlightmanger.dialog.DeleteDialog
import com.example.moodlightmanger.dialog.DeleteDialogInterface
import com.example.moodlightmanger.model.SuccessResponseModel
import com.example.moodlightmanger.model.question.AllQuestionModel
import com.example.moodlightmanger.model.question.AllQuestionModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class QuestionActivity : AppCompatActivity(), DeleteDialogInterface {
    private lateinit var deleteDialog : DeleteDialog
    private lateinit var deleteData : AllQuestionModelItem
    private var deleteIndex by Delegates.notNull<Int>()
    private lateinit var questionList : AllQuestionModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        deleteDialog = DeleteDialog(this, this, "질문 삭제하기", "질문을 삭제하시겠습니까?", "삭제", "취소")
        ServerClient.getApiService().getQuestionAll()
            .enqueue(object : Callback<AllQuestionModel> {

                override fun onResponse(
                    call: Call<AllQuestionModel>,
                    response: Response<AllQuestionModel>,
                ) {
                    if (response.isSuccessful) {

                        questionList = response.body()!!


                        val questionAdapter : QuestionAdapter = QuestionAdapter(questionList){position, data ->
                            deleteDialog.show()
                            deleteData = data
                            deleteIndex = position
                        }
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

    override fun onCheckBtnClick() {
        deleteQuestion()
        deleteDialog.dismiss()
    }

    override fun onCancleBtnClick() {
        deleteDialog.dismiss()
    }

    fun deleteQuestion(){
        ServerClient.getApiService().deleteData(deleteData.id).enqueue(object :
        Callback<SuccessResponseModel>{
            override fun onResponse(
                call: Call<SuccessResponseModel>,
                response: Response<SuccessResponseModel>
            ) {
                val result = response.body()
                Log.d(TAG, "onResponse: $response")
                if(response.code() == 200){
                    if(!result!!.success){
                        Toast.makeText(this@QuestionActivity, "질문 삭제에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@QuestionActivity, "질문 삭제에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                        questionList.remove(deleteData)

                        findViewById<RecyclerView>(R.id.queiston_recyclerview)
                            .adapter!!.notifyItemRemoved(deleteIndex)
                    }
                }
                else{
                    Toast.makeText(this@QuestionActivity, "질문 삭제에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SuccessResponseModel>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@QuestionActivity, "질문을 삭제하는 도중 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show()
            }

        })

    }
}