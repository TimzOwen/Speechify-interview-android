package com.speechify.composeuichallenge.ui.screens.BookScreen

import androidx.annotation.StyleRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.speechify.composeuichallenge.ui.screens.BookScreen.components.BooksCardComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    viewModel: BooksViewModel = hiltViewModel(),
    onNavigate: (String, String, String) -> Unit
) {

    val booksUiState by viewModel.booksUiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                { Text("Books") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(18.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchQuery,
                onValueChange = {
                    it = searchQuery,
                    viewModel.searchBooksQuery(searchQuery)
                                },
            )

            when (booksUiState) {
                is BookUiState.Loading -> LoadingComponent()
                is BookUiState.Error -> ErrorComponent(message = (booksUiState as BookUiState.Error).message)
                is BookUiState.Success -> LoadBooks(
                    (booksUiState as BookUiState.Success).books,
                    onClick = { onNavigate(it.imageUrl, it.name, it.description) }
                )
            }
        }
    }
}

@Composable
fun LoadBooks(books: List<Book>, onClick: (book: Book) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        items(books) { book ->
            BooksCardComponent(book) { onClick(book) }
        }
    }
}

@Composable
fun ErrorComponent(modifier: Modifier = Modifier, message: String) {
    Box(modifier.fillMaxSize(), Alignment.Center) {
        Text(message)
    }
}

@Composable
fun LoadingComponent(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), Alignment.Center) {
        CircularProgressIndicator()
    }
}