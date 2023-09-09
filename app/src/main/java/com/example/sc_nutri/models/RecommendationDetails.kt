package com.example.sc_nutri.models

data class RecommendationDetails(
    val recommendation_result: String,
    val reasons: ArrayList<String>,
    val notable_ingredients: ArrayList<String>,
    val nutritional_analysis: ArrayList<String>
)
