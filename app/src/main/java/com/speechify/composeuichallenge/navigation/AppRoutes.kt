package com.speechify.composeuichallenge.navigation

import com.speechify.composeuichallenge.data.Book
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class DetailsRoute(
    val imageUrl: String,
    val author: String,
    val desc: String
)