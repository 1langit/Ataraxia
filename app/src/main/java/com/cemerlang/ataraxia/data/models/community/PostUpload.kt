package com.cemerlang.ataraxia.data.models.community

data class PostUpload(
    val title: String,
    val body: String,
    val category: List<String>?,
    val image: String?
)