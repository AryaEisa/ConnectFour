package se.ju.DIMP2022.group28.connecfour

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import io.garrit.android.multiplayer.Message
import kotlinx.serialization.SerialName
import se.ju.DIMP2022.group28.connecfour.ui.theme.ConnecFourTheme
import java.util.UUID



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            ConnecFourTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mediaPlayer: MediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.click)

                    DisposableEffect(Unit) {
                        onDispose {
                            mediaPlayer.release()
                        }

                    }

                    NavPage()


                }
            }
        }
    }
}
