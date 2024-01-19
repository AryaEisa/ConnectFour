package se.ju.DIMP2022.group28.connecfour.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.launch
import se.ju.DIMP2022.group28.connecfour.R
/*
i viewModel vi bygger egentliga funktioner, såsom om en knapp trycks ska nåt hända,
inget som behöver composable egentligen.

 */
class ImageViewModel : ViewModel() {
    val imageResource = listOf(
        1 to R.drawable.image1,
        2 to R.drawable.image2,
        3 to R.drawable.image3,
        4 to R.drawable.image4,
        5 to R.drawable.image5,
        6 to R.drawable.image6,
        7 to R.drawable.image7,
        8 to R.drawable.image8,
        9 to R.drawable.image9,
        10 to R.drawable.image10,
        11 to R.drawable.image11,
        12 to R.drawable.image12
    )

    private val _selectIndex = mutableStateOf(0)
    val selectIndex: State<Int> = _selectIndex

    fun chooseNextImage() {
        val newIndex = (_selectIndex.value + 1) % imageResource.size
        _selectIndex.value = newIndex
    }

    fun choosePreviousImage() {
        val newIndex = if (_selectIndex.value > 0) _selectIndex.value - 1 else imageResource.size - 1
        _selectIndex.value = newIndex
    }
}

