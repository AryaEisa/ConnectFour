package se.ju.DIMP2022.group28.connecfour.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.RowScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.garrit.android.multiplayer.Player
import se.ju.DIMP2022.group28.connecfour.viewModels.EnterLobbyViewModel
import se.ju.DIMP2022.group28.connecfour.viewModels.ImageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterLobbyScreen(navController: NavHostController = rememberNavController()) {
    var playerName by rememberSaveable { mutableStateOf("") }
    val viewModel = viewModel<EnterLobbyViewModel>()
    val cViewModel = viewModel<ImageViewModel>()
    val gradient = listOf(Color.LightGray, Color.DarkGray, Color.Blue, Color.Black, Color.Green)

    Column(
        modifier = Modifier
            .background(brush = Brush.linearGradient(gradient))
            .fillMaxSize()
    ) {
        Row {
            Button(
                onClick = { navController.navigateUp() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageColumn(imageViewModel = cViewModel)
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            ) {
                TextField(
                    value = playerName,
                    onValueChange = { playerName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    label = { Text(text = "Choose character and write your name") }
                )
                Column {


                    Button(
                        onClick = {
                            val player =
                                Player(name = playerName)
                            viewModel.enterLobby(player)
                            navController.navigate(Screen.Lobby.route)
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                start = 100.dp,
                                end = 100.dp
                            )
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                Color.Gray,
                                shape = RoundedCornerShape(percent = 50)
                            )
                    ) {
                        Text(text = "Join Lobby", color = Color.White)
                    }

                }
            }
        }
    }
}





@Composable
fun ImageColumn(imageViewModel: ImageViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val selectedImageIndex = imageViewModel.selectIndex.value
        Image(
            painter = painterResource(id = imageViewModel.imageResource[selectedImageIndex].second),
            contentDescription = "Image $selectedImageIndex",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { imageViewModel.choosePreviousImage() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Image")
            }

            IconButton(
                onClick = { imageViewModel.chooseNextImage() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Image")
            }
        }
    }
}


@Preview
@Composable
fun EnterLobbyPre() {
    EnterLobbyScreen()

}