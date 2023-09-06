package com.example.sc_nutri.models

data class User(
    val gender: String,
    val age: Int,
    val weight: Int,
    val height: Int,
    val fitness_activity: String,
    val allergies: ArrayList<String>,
    val preferred_diet: String,
    val weight_gain: String
)
