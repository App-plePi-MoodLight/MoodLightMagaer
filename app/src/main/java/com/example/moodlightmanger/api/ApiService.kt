package com.example.moodlightmanger.api

import com.example.moodlightmanger.model.*
import com.example.moodlightmanger.model.question.AllQuestionModel
import retrofit2.Call
import retrofit2.http.*

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

    @PUT("question")
    fun editQuestion(@Body editQuestionModel: EditQuestionModel) : Call<SuccessResponseModel>

    @DELETE("question/{questionId}")
    fun deleteData(@Path("questionId") questionId : String) : Call<SuccessResponseModel>
}