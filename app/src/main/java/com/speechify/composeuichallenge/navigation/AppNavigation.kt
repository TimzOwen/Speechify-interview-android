package com.speechify.composeuichallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.speechify.composeuichallenge.ui.screens.BookScreen.BookScreen
import com.speechify.composeuichallenge.ui.screens.DetailScreen.DetailsScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = Home) {

        composable<Home> {
            BookScreen(
                onNavigate = { imageUrl, name, desc ->
                    navController.navigate(Details(imageUrl, name, desc))
                }
            )
        }

        composable<Details> {
            val args = it.toRoute<Details>()
            DetailsScreen(
                imageUrl = args.imageUrl,
                name = args.name,
                desc = args.desc
            )
        }
    }

}