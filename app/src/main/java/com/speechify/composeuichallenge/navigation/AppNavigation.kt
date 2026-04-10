package com.speechify.composeuichallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.speechify.composeuichallenge.repository.Details
import com.speechify.composeuichallenge.ui.screens.details.BooksDetailsScreen
import com.speechify.composeuichallenge.ui.screens.home.BooksHomeScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = HomeRoute) {

        composable<HomeRoute> {
            BooksHomeScreen(
                onNavigate = { imageUrl, author, desc ->
                    navController.navigate(DetailsRoute(imageUrl, author, desc))
                }
            )
        }

        composable<DetailsRoute> {
            val args = it.toRoute<DetailsRoute>()
            BooksDetailsScreen(
                imageUrl = args.imageUrl,
                author = args.author,
                desc = args.desc
            )
        }

    }
}