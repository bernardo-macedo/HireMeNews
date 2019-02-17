package com.bmacedo.hiremenews.models.domain

data class NewsSource(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val url: String? = null,
    val category: String? = null,
    val language: String? = null,
    val country: String? = null
)