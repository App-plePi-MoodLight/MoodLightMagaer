package com.example.moodlightmanger.api

import com.example.moodlightmanger.model.AuthModel
import com.example.moodlightmanger.model.LoginModel
import com.example.moodlightmanger.model.QuestionCreateModel
import com.example.moodlightmanger.model.question.AllQuestionModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    fun login(@Body loginModel: LoginModel): Call<LoginModel>

    @POST("question")
    fun createQuestion(@Body questionCreateModel: QuestionCreateModel)
    : Call<QuestionCreateModel>

    @GET("auth")
    fun getUserInformation() : Call<AuthModel>

    @GET("question")
    fun getQuestionAll() : Call<AllQuestionModel>
}