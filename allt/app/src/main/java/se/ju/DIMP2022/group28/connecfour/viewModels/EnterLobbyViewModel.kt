package se.ju.DIMP2022.group28.connecfour.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.launch
//e
class EnterLobbyViewModel : ViewModel() {
    fun enterLobby(player: Player){
        viewModelScope.launch {
            SupabaseService.joinLobby(player)
        }
    }
}