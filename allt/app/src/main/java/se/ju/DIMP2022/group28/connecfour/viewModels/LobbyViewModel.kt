package se.ju.DIMP2022.group28.connecfour.viewModels

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.garrit.android.multiplayer.Game
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.launch
import java.lang.reflect.Array.get


class LobbyViewModel : ViewModel(){
    val users: SnapshotStateList<Player> = SupabaseService.users
    private val _players = mutableStateListOf<Player>()
    val players: SnapshotStateList<Player> get() = _players
    val games: SnapshotStateList<Game> = SupabaseService.games
    val player: Player?
        get() = SupabaseService.player

    fun sendInvite(player: Player){
        viewModelScope.launch {
            SupabaseService.invite(player)
        }
    }
    fun AcceptInvite(game: Game){
        viewModelScope.launch {
            SupabaseService.acceptInvite(game)
        }
    }
    fun DeclineInvite(game: Game){
        viewModelScope.launch {
            SupabaseService.declineInvite(game)
        }
    }
}