package com.speechify.composeuichallenge.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Details(
    val imageUrl: String,
    val name: String,
    val desc: String
)