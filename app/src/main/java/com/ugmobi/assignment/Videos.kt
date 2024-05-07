package com.ugmobi.assignment

import kotlinx.serialization.Serializable

@Serializable
data class Videos(
    val id: Int,
    val ch_name: String,
    val title: String,
    val description: String,
    val likes: Int,
    val thumbnail: String,
    val videourl: String
)
