package com.example.moodlightmanger.model

data class QuestionModelItem(
    val activated: Boolean,
    val activatedDate: String,
    val contents: String,
    val id: String,
    val mood: String
)