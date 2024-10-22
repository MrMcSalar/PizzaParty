package edu.farmingdale.pizzapartybottomnavbar

import PizzaPartyScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(
    navController: NavHostController,
    onVisibilityChange: (Boolean) -> Unit
) {
    NavHost(navController = navController, startDestination = "pizza_party") {

        composable("pizza_party") {
            PizzaPartyScreen()
            onVisibilityChange(true)
        }

        composable("other_screen") {
            onVisibilityChange(false)
        }
    }
}
