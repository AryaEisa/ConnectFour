    package se.ju.DIMP2022.group28.connecfour.viewModels

    import androidx.compose.runtime.MutableState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateListOf
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.ui.graphics.Color
    import androidx.compose.runtime.setValue
    import androidx.compose.runtime.toMutableStateList
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import io.garrit.android.multiplayer.ActionResult
    import io.garrit.android.multiplayer.GameResult
    import io.garrit.android.multiplayer.Message
    import io.garrit.android.multiplayer.Player
    import io.garrit.android.multiplayer.SupabaseCallback
    import io.garrit.android.multiplayer.SupabaseService
    import kotlinx.coroutines.delay
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.launch
    import se.ju.DIMP2022.group28.connecfour.screens.Screen


    class GameViewModel() :  ViewModel(), SupabaseCallback {

        var isWin: MutableState<Boolean> = mutableStateOf(false)
        var isLoss: MutableState<Boolean> = mutableStateOf(false)
        var isDraw: MutableState<Boolean> = mutableStateOf(false)
        var currentPlayer by mutableStateOf<Player?>(SupabaseService.currentGame?.player1)

        var buttonColors = mutableStateListOf<Color>().apply {
            repeat(6 * 7) {
                add(Color.Blue)
            }
        }
        init {
            SupabaseService.callbackHandler = this
        }
        override suspend fun playerReadyHandler() {
            viewModelScope.launch {

            }
        }

        override suspend fun releaseTurnHandler() {
            viewModelScope.launch {

            }
        }

        override suspend fun actionHandler(x: Int, y: Int) {


            viewModelScope.launch {
                // Inside the clickable block, directly update UI based on the incoming move
                val col = x
                val row = rowDown(buttonColors, col)

                if (row < 0) {
                    println("FUSK!!!")
                    return@launch
                }

                val color: Color
                val nextPlayer: Player?

                if (currentPlayer?.id == SupabaseService.currentGame?.player1?.id) {
                    color = Color.Red
                    nextPlayer = SupabaseService.currentGame?.player2
                } else {
                    color = Color.Yellow
                    nextPlayer = SupabaseService.currentGame?.player1
                }
                buttonColors = buttonColors.toMutableList().apply {
                    set(row * 7 + col, color)
                }.toMutableStateList()
                //e
                currentPlayer = nextPlayer

                // Update UI as needed
                skrivUtBrade(buttonColors)

                // Check for a winner

            }
        }



        override suspend fun finishHandler(status: GameResult) {
            when(status){
                GameResult.WIN->{
                    isWin.value = true
                    isLoss.value = false
                }
                GameResult.LOSE->{
                    isWin.value = false
                    isLoss.value = true
                }
                GameResult.DRAW->{
                    isWin.value = false
                    isLoss.value = false
                    isDraw.value = true
                }
                else->{

                }
            }
        }
        var chatMessages = mutableStateListOf<Message>()
        override suspend fun messageHandler(message: Message) {
            viewModelScope.launch{
                chatMessages.add(message)
            }
        }


        fun onSendMessage(messageText: String) {
            // Ensure currentPlayer is not null before sending a message
            val currentPlayer = currentPlayer ?: return

            // Create a Message object with the current player and the entered text
            val message = Message(sender = currentPlayer, message = messageText)

            // Add the message to the chatMessages list
            chatMessages.add(message)

            // Send the message using SupabaseService
            sendMessage(message)
        }



        fun recivedMessage(message: Message) {
            viewModelScope.launch {
                recivedMessage(message)
            }
        }

        fun sendMessage(message: Message){
            viewModelScope.launch {
                SupabaseService.sendMessage(message)
            }
        }

        fun sendTurn(x: Int) {
            viewModelScope.launch {
                SupabaseService.sendTurn(x)
                if (checkWin()) {
                    println("WIN!")
                    SupabaseService.gameFinish(GameResult.LOSE)
                    isWin.value = true
                    isLoss.value = false
                    isDraw.value = false

                } else if (checkDraw()) {
                    SupabaseService.gameFinish(GameResult.DRAW)
                    isWin.value = false
                    isLoss.value = false
                    isDraw.value = true
                } else {
                    println("RELEASE!")
                    SupabaseService.releaseTurn()
                }
            }



        }
        override suspend fun answerHandler(status: ActionResult) {
        }

        fun playerReady() {
            viewModelScope.launch {
                SupabaseService.playerReady()
            }
        }

        suspend fun releaseTurn() {
            viewModelScope.launch {
                SupabaseService.releaseTurn()
            }
        }



        fun onCellClick(navController: NavController, columnIndex: Int) {
            val rowIndex = rowDown(buttonColors, columnIndex)

            if (rowIndex >= 0) {
                val color: Color
                val nextPlayer: Player?
                if (currentPlayer == null) {
                    // Handle the case when currentPlayer is null (for initialization, error handling, etc.)
                    color = Color.Blue // or any other appropriate color
                    nextPlayer = SupabaseService.currentGame?.player1
                } else if (currentPlayer!!.id == SupabaseService.currentGame?.player1?.id) {
                    color = Color.Red
                    nextPlayer = SupabaseService.currentGame?.player2
                } else {
                    color = Color.Yellow
                    nextPlayer = SupabaseService.currentGame?.player1
                }

                buttonColors = buttonColors.toMutableList().apply {
                    set(rowIndex * 7 + columnIndex, color)
                }.toMutableStateList()
                sendTurn(columnIndex)
                currentPlayer = nextPlayer
            }

        }

        private val _newMessageReceived = MutableStateFlow<Pair<Boolean, Player?>>(false to null)
        val newMessageReceived: StateFlow<Pair<Boolean, Player?>> = _newMessageReceived
        fun onNewMessageReceived(sender: Player?) {
            viewModelScope.launch {
                _newMessageReceived.emit(true to sender)
            }
        }


        fun rowDown(list: List<Color>, columnIndex: Int): Int {
            for(y in 0..5) {
                if(list[y * 7 + columnIndex] != Color.Blue)
                    return y - 1
            }
            return 5
        }


         fun skrivUtBrade(list: List<Color>) {

            for (i in 0 until 6) {
                var str = "";
                for (j in 0 until 7) {
                    val index = i * 7 + j
                    val currentColor = list[index]
                    str += " ${if (currentColor == Color.Red) 1 else if (currentColor == Color.Yellow) 2 else 0}"
                }
                println("$i $str")
            }
        }

       fun checkWin(): Boolean {
            val player = when (currentPlayer?.id) {
                SupabaseService.currentGame?.player1?.id -> Color.Red
                SupabaseService.currentGame?.player2?.id -> Color.Yellow
                else -> return false
            }
           println("CHECK WIN")

            val rows = 6
            val columns = 7

            // Check horizontal
            for (row in 0 until rows) {
                for (column in 0 until columns - 3) {
                    if (
                        buttonColors[row * columns + column] == player &&
                        buttonColors[row * columns + column + 1] == player &&
                        buttonColors[row * columns + column + 2] == player &&
                        buttonColors[row * columns + column + 3] == player
                    ) {
                        return true
                    }
                }
            }

            // Check vertical
            for (row in 0 until rows - 3) {
                for (column in 0 until columns) {
                    if (
                        buttonColors[row * columns + column] == player &&
                        buttonColors[(row + 1) * columns + column] == player &&
                        buttonColors[(row + 2) * columns + column] == player &&
                        buttonColors[(row + 3) * columns + column] == player
                    ) {
                        return true
                    }
                }
            }

            // Check diagonal (bottom-left to top-right)
            for (row in 0 until rows - 3) {
                for (column in 0 until columns - 3) {
                    if (
                        buttonColors[row * columns + column] == player &&
                        buttonColors[(row + 1) * columns + column + 1] == player &&
                        buttonColors[(row + 2) * columns + column + 2] == player &&
                        buttonColors[(row + 3) * columns + column + 3] == player
                    ) {
                        return true
                    }
                }
            }

            // Check diagonal (bottom-right to top-left)
            for (row in 0 until rows - 3) {
                for (column in 3 until columns) {
                    if (
                        buttonColors[row * columns + column] == player &&
                        buttonColors[(row + 1) * columns + column - 1] == player &&
                        buttonColors[(row + 2) * columns + column - 2] == player &&
                        buttonColors[(row + 3) * columns + column - 3] == player
                    ) {
                        return true
                    }
                }
            }

            return false
        }
        fun checkDraw(): Boolean {
            return buttonColors.all { it != Color.Blue }
        }

        fun checkWinner(navController: NavController) {
            val winner = checkWin()


            if (winner ) {
                when (currentPlayer?.id) {
                    SupabaseService.currentGame?.player1?.id -> {
                        println("Red won!")
                        navController.navigate(Screen.WinnerPage.route + "/Red" + "/" + SupabaseService.currentGame?.player1?.name)
                    }
                    SupabaseService.currentGame?.player2?.id -> {
                        println("Yellow won!")
                        navController.navigate(Screen.WinnerPage.route + "/Yellow" + "/" + SupabaseService.currentGame?.player2?.name)
                    }
                }
            } else {
                // Handle draw or any other case
            }
        }
    }
