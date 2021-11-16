package com.example.moodlightmanger.model.question

import java.io.Serializable

data class AllQuestionModelItem (
    val activated: Boolean,
    val activatedDate: String,
    val contents: String,
    val id: String,
    val mood: String
) : Serializable