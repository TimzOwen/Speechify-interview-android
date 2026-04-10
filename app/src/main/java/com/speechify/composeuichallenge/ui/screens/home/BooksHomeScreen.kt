package com.speechify.composeuichallenge.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.speechify.composeuichallenge.data.Book
import com.speechify.composeuichallenge.ui.screens.home.components.BookCardComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksHomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String, String, String) -> Unit
) {

    val uiState by viewModel.booksUIState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            { Text("Books") }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text("Search books...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            when (uiState) {
                is BooksUiState.Loading -> LoadingState()
                is BooksUiState.Error -> ErrorState((uiState as BooksUiState.Error).message)
                is BooksUiState.Success -> LoadBooks(
                    (uiState as BooksUiState.Success).books,
                    onClick = { onNavigate(it.imageUrl, it.author, it.description) }
                )
            }
        }
    }
}

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}

@Composable
fun LoadBooks(books: List<Book>, onClick: (book: Book) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books) { book ->
            BookCardComponent(book, onClick = { onClick(book) })
        }
    }
}