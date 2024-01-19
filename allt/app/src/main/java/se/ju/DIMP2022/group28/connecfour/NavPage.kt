package se.ju.DIMP2022.group28.connecfour

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.garrit.android.multiplayer.ServerState
import io.garrit.android.multiplayer.SupabaseService
import se.ju.DIMP2022.group28.connecfour.screens.DrawPage

import se.ju.DIMP2022.group28.connecfour.screens.EnterLobbyScreen
import se.ju.DIMP2022.group28.connecfour.screens.GameScreen

import se.ju.DIMP2022.group28.connecfour.screens.LobbyScreen
import se.ju.DIMP2022.group28.connecfour.screens.LoserPage
import se.ju.DIMP2022.group28.connecfour.screens.MainScreen

import se.ju.DIMP2022.group28.connecfour.screens.Screen
import se.ju.DIMP2022.group28.connecfour.screens.WinnerPage
//import se.ju.DIMP2022.group28.connecfour.screens.enterLobbyScreen


//import se.ju.DIMP2022.group28.connecfour.viewModels.GameViewModel

@Composable
fun NavPage() {
    val navController = rememberNavController()
    stateNav(navController)
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route) {
            MainScreen(navController = navController)
        }

        composable(route = Screen.Game.route) {
            GameScreen(navController)
        }

        composable(route = Screen.WinnerPage.route ) {


            WinnerPage(navController = navController)
        }

        composable(route = Screen.EnterLobby.route) {
            EnterLobbyScreen(navController = navController)
        }

        composable(route = Screen.Lobby.route) {

            LobbyScreen(navController = navController)
        }
        composable(route = Screen.LoserPage.route) {

            LoserPage(navController = navController)
        }
        composable(route = Screen.DrawPage.route) {
            DrawPage(navController = navController)
        }



    }
}
@Composable
fun stateNav(navController: NavController = rememberNavController()){
    val serverState by SupabaseService.serverState.collectAsState()
    LaunchedEffect(serverState){
        println(serverState)
        when(serverState){
            ServerState.NOT_CONNECTED ->{
                navController.navigate(Screen.Main.route)
            }
            ServerState.LOADING_LOBBY -> {
                //navController.navigate(Screen.EnterLobby.route)
            }
            ServerState.LOBBY -> {
                navController.navigate(Screen.Lobby.route)
            }
            ServerState.GAME -> {
                navController.navigate(Screen.Game.route)
            }
            else -> {}
        }
    }
}
