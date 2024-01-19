package se.ju.DIMP2022.group28.connecfour.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import se.ju.DIMP2022.group28.connecfour.viewModels.LobbyViewModel

@Composable
fun LobbyScreen(navController: NavController = rememberNavController()){
    val viewModel = viewModel<LobbyViewModel>()
    var invitedPlayers by remember { mutableStateOf(setOf<String>()) }
    var messageDuration by remember { mutableStateOf(2000L) }
    val gradient = listOf(Color.LightGray, Color.DarkGray, Color.Blue, Color.Black, Color.Green)

    Column (modifier = Modifier
        .fillMaxSize()
        .background(brush = Brush.linearGradient(gradient))){
        Column {
            Button(onClick = { navController.navigateUp() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray))
            {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")

            }
        }
        Row (modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center)
        {
            Text(text = "Connected Players",
                style = TextStyle(Color.Black),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())
        }

        LazyColumn() {
            items(viewModel.users.filter { it != viewModel.player }) { connectedPlayer ->
                val coroutineScope = rememberCoroutineScope()

                // Track whether the player is invited
                var isInvited by remember { mutableStateOf(false) }

                // Animate the background color change
                val backgroundColor by animateColorAsState(
                    if (isInvited) Color.Green else Color.LightGray
                )

                Text(
                    text = connectedPlayer.name,
                    style = TextStyle(Color.Black),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable {
                            isInvited = !isInvited

                            invitedPlayers = invitedPlayers
                                .toMutableSet()
                                .apply {
                                    if (isInvited) {
                                        add(connectedPlayer.name)
                                    } else {
                                        remove(connectedPlayer.name)
                                    }
                                }

                            viewModel.sendInvite(connectedPlayer)

                            coroutineScope.launch {
                                delay(messageDuration)
                                isInvited = false
                            }
                        }
                        .padding(16.dp)
                        .background(backgroundColor)
                        .fillMaxWidth()
                        .padding(20.dp)
                )

                if (invitedPlayers.contains(connectedPlayer.name)) {
                    Text(
                        text = "Invited ${connectedPlayer.name}",
                        style = TextStyle(Color.Green)
                    )
                }
            }
        }

        LazyColumn{
            items(viewModel.games)
            {game->
                Text(text = "Invite : ${game.player1.name}",
                    style = TextStyle(Color.Black),
                    fontWeight = FontWeight.Bold)

                Row {
                    Button(onClick = {viewModel.AcceptInvite(game)
                    },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green))
                    {

                        Text(text = "Accept",
                            style = TextStyle(Color.Black),
                            fontWeight = FontWeight.Bold)
                    }
                    Button(onClick = {viewModel.DeclineInvite(game)

                    },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red))
                    {
                        Text(text = "Decline",
                            style = TextStyle(Color.White),
                            fontWeight = FontWeight.Bold)
                    }
                }

            }
        }
    }

}


@Preview
@Composable
fun LobbyScreenPre() {
    LobbyScreen()

}