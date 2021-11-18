package com.kanyiakinyidevelopers.goals.models

data class Goal(
    val bgColor: String? = "",
    val title: String? = "",
    val description: String? = "",
    val dateTime: String? = "",
    val poster: String? = "",
    val isAchieved: Boolean = false
)