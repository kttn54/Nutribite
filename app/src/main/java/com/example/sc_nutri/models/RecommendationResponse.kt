package com.example.sc_nutri.models

data class RecommendationResponse(
    val recommendation_response: String,
    val recommendation_result: String,
    val reasons: ArrayList<String>
)

/*
{
    "recommendation-response": "insert string of recommendation",
    "recommendation-result": "yes",
    "reasons": ["Reason 1", "Reason 2", "Reason 3"]
}
 */
