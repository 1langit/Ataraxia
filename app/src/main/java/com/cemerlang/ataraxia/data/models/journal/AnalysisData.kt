package com.cemerlang.ataraxia.data.models.journal

data class AnalysisData(
    val id: Int,
    val journal_id: Int,
    val body: String,
    val created_at: String,
    val updated_at: String
)