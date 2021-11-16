package com.example.moodlightmanger.model

data class EditQuestionModel(
    val questionId: String,
    val mood: String,
    val activatedDate: String,
    val contents: String
)