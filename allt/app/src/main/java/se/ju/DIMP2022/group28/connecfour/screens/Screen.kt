package se.ju.DIMP2022.group28.connecfour.screens

sealed class Screen(val route: String){
    object Main : Screen(route = "main")
    object PlayList: Screen(route = "playList")
    object Game : Screen(route = "game")
    object WinnerPage: Screen(route = "WinnerPage")
    object LoserPage: Screen(route = "LoserPage")
    object DrawPage: Screen(route = "DrawPage")
    object Lobby : Screen(route = "lobby")
    object EnterLobby : Screen(route = "enterlobby")

}
