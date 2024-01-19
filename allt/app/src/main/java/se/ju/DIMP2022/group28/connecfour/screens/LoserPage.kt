package se.ju.DIMP2022.group28.connecfour.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import se.ju.DIMP2022.group28.connecfour.R

@Composable
fun LoserPage(navController: NavController) {

    val gradient = listOf( Color.Red, Color.Black, Color.Red)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(gradient))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        // Winner title
        Text(
            text = "Loser is:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        // Winner's color and name
        Text(
            text = "You!!!",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

        // Back to challenge button
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate(Screen.Main.route) },
            modifier = Modifier.width(150.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = "Back to challenge",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun Pre() {
    LoserPage(navController = rememberNavController())
}