package com.speechify.composeuichallenge.ui.screens.home

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
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BooksRepository
) : ViewModel() {

    private val _booksUIState = MutableStateFlow<BooksUiState>(BooksUiState.Loading)
    val booksUIState: StateFlow<BooksUiState> = _booksUIState.asStateFlow()

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
                _booksUIState.value = BooksUiState.Success(books)
            } catch (e: IOException) {
                _booksUIState.value = BooksUiState.Error(e.message.toString())
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            try {
                _booksUIState.value = BooksUiState.Loading
                val books = if (query.isEmpty()) {
                    repository.getBooks()
                } else {
                    repository.searchBook(query)
                }
                _booksUIState.value = BooksUiState.Success(books)
            } catch (e: IOException) {
                _booksUIState.value = BooksUiState.Error(e.message.toString())
            }
        }
    }
}