package com.speechify.composeuichallenge.ui.screens.home

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.speechify.composeuichallenge.repository.BooksRepository
import com.speechify.composeuichallenge.ui.screens.home.BooksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: BooksRepository
) : ViewModel() {

    private val _booksUIState = MutableStateFlow<BooksUiState>(BooksUiState.Loading)
    val booksUIState: StateFlow<BooksUiState> = _booksUIState

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            try {
                val books = repository.getBooks()
                _booksUIState.value = BooksUiState.Success(books)
            } catch (e: IOException) {
                _booksUIState.value = BooksUiState.Error(e.message.toString())
            }
        }
    }
}