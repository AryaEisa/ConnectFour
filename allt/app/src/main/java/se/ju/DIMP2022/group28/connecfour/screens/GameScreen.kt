    package se.ju.DIMP2022.group28.connecfour.screens

    import android.media.MediaPlayer
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.aspectRatio
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.pager.HorizontalPager
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Send
    import androidx.compose.material3.Icon
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.DisposableEffect
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateListOf
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.rememberUpdatedState
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Brush
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import io.garrit.android.multiplayer.GameResult
    import io.garrit.android.multiplayer.Message
    import io.garrit.android.multiplayer.Player
    import io.garrit.android.multiplayer.SupabaseService
    import io.ktor.client.utils.EmptyContent.status
    import kotlinx.coroutines.delay
    import se.ju.DIMP2022.group28.connecfour.R
    import se.ju.DIMP2022.group28.connecfour.viewModels.GameViewModel
/*
 if (viewModel.isWin.value){
                navController.navigate(Screen.WinnerPage.route)
            }
            else if (viewModel.isLoss.value){
                navController.navigate(Screen.LoserPage.route)
            }
            else if (viewModel.isDraw.value){
                navController.navigate(Screen.DrawPage.route)
            }
 */
    @Composable
    fun GameScreen(navController: NavController, viewModel: GameViewModel = viewModel()) {
        val currentPlayer = viewModel.currentPlayer
        val buttonColors = viewModel.buttonColors

        val gradient = listOf(Color.LightGray, Color.DarkGray, Color.Blue, Color.Black, Color.Green)

        val updatedButtonColors by rememberUpdatedState(viewModel.buttonColors)

        val mediaPlayer: MediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.click)

        DisposableEffect(Unit) {
            onDispose {
                mediaPlayer.release()
            }
        }

        LaunchedEffect(viewModel.isWin.value, viewModel.isLoss.value, viewModel.isDraw.value) {
            //delay(100) // Add a short delay to allow UI to update

            if (viewModel.isWin.value){
                navController.navigate(Screen.WinnerPage.route)
            }
            else if (viewModel.isLoss.value){
                navController.navigate(Screen.LoserPage.route)
            }
            else if (viewModel.isDraw.value){
                navController.navigate(Screen.DrawPage.route)
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(gradient))
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                GameBoard(buttonColors, currentPlayer, viewModel, navController, mediaPlayer)
            }

            Spacer(modifier = Modifier.height(16.dp))

            ChatSection(viewModel)
        }
    }

    @Composable
    fun GameBoard(
        buttonColors: List<Color>,
        currentPlayer: Player?,
        viewModel: GameViewModel,
        navController: NavController,
        mediaPlayer: MediaPlayer
    ) {
        val player1 = SupabaseService.currentGame?.player1?.name
        val player2 = SupabaseService.currentGame?.player2?.name

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PlayerInfo(player1, "Red", currentPlayer)
                PlayerInfo(player2, "Yellow", currentPlayer)
            }

            for (i in 0 until 6) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    for (j in 0 until 7) {
                        val index = i * 7 + j
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(Color.DarkGray)
                                .padding(vertical = 1.dp, horizontal = 1.dp)
                                .clip(CircleShape)
                                .background(buttonColors[index], shape = CircleShape)
                                .clickable(enabled = currentPlayer == SupabaseService.player) {
                                    mediaPlayer.start()
                                    viewModel.onCellClick(navController, j)
                                }
                        ) {
                            // Add any content inside the box if needed
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PlayerInfo(playerName: String?, colorName: String, currentPlayer: Player?) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Player: $playerName",
                color = if (colorName == "Red") Color.Red else Color.Yellow,
                fontWeight = FontWeight.Bold
            )
            if (currentPlayer?.name == playerName) {
                Text(text = "Current Turn", color = Color.Black, fontSize = 20.sp)
            }
        }
    }


    @Composable
    fun ChatSection(viewModel: GameViewModel) {
        var isChatVisible by remember { mutableStateOf(false) }
        Column (modifier = Modifier.background(Color.White)){


            Column {
                // Chat Icon
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Chat",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            isChatVisible = !isChatVisible
                        }
                )

                // Chat Section
                if (isChatVisible) {
                    // Replace the following with your actual chat messages
                    val chatMessage = viewModel.chatMessages

                    // Display chat history
                    Conversation(messages = chatMessage)

                    // Input field for typing messages
                    ChatInput(viewModel, onSendMessage = { message ->
                        if (viewModel.currentPlayer == null) {
                            return@ChatInput
                        }

                        val msg = Message(sender = viewModel.currentPlayer!!, message = message)
                        chatMessage.add(msg)
                        viewModel.sendMessage(msg)
                    })
                }
            }
        }
    }



