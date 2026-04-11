package com.speechify.composeuichallenge.ui.screens.BookScreen

import com.speechify.composeuichallenge.data.Book

sealed interface BookUiState {
    data object Loading : BookUiState
    data class Success(val books: List<Book>) : BookUiState
    data class Error(val message: String) : BookUiState
}