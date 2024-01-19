package se.ju.DIMP2022.group28.connecfour.screens

import android.graphics.Paint.Style
import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import se.ju.DIMP2022.group28.connecfour.R



@Composable
fun MainScreen(navController: NavController = rememberNavController() ) {
    val mediaPlayer: MediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.click)

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
    val gradient = listOf(Color.LightGray, Color.DarkGray, Color.Blue, Color.Black, Color.Green)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = Brush.linearGradient(gradient))) {

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.galaxy),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val textVisibleState = remember { MutableTransitionState(false) }
                val transition = updateTransition(textVisibleState, label = "")
                Text(
                    text = "CONNECT 4",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 35.sp,
                        shadow = Shadow(
                        color = Color.Yellow, offset = Offset(0f, 0f), blurRadius = 100f
                        )
                    )

                )

                Icon(
                    imageVector = Icons.Default.Face,
                    tint = Color.Yellow,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(13.dp)
                        .size(70.dp)
                )



                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        mediaPlayer.start()
                        navController.navigate(Screen.EnterLobby.route)
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.width(200.dp),
                    shape = RoundedCornerShape(50)

                ) {
                    Text(text = "PLAY",
                        style = TextStyle(color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 30.sp,
                            shadow = Shadow(
                                color = Color.Yellow, offset = Offset(0f, 0f), blurRadius = 1f
                            )
                        )
                    )
                }
            }
        }
    }
}
//e
@Preview
@Composable
fun MainScreenPre() {
    MainScreen()
}