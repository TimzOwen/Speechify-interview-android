package com.speechify.composeuichallenge.ui.screens.BookScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.speechify.composeuichallenge.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    val repository: BooksRepository
) : ViewModel() {

    private val _booksUiState = MutableStateFlow<BookUiState>(BookUiState.Loading)
    val booksUiState: StateFlow<BookUiState> = _booksUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            try {
                val books = repository.getBooks()
                _booksUiState.value = BookUiState.Success(books)
            } catch (e: IOException) {
                _booksUiState.value = BookUiState.Error(e.message.toString())
            }
        }
    }

    fun searchBooksQuery(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            try {
                _booksUiState.value = BookUiState.Loading
                val books = if (query.isBlank()) {
                    repository.getBooks()
                } else {
                    repository.searchBook(query)
                }
                _booksUiState.value = BookUiState.Success(books)
            } catch (e: IOException) {
                _booksUiState.value = BookUiState.Error(e.message.toString())
            }
        }
    }
}