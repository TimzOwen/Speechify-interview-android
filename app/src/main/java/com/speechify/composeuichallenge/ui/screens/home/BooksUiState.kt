package com.speechify.composeuichallenge.ui.screens.home

import com.speechify.composeuichallenge.data.Book

sealed interface BooksUiState {
    object Loading : BooksUiState
    data class Success(val books: List<Book>) : BooksUiState
    data class Error(val message: String) : BooksUiState
}