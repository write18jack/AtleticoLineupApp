package com.example.atleticolineupapp.ui.tab

import android.app.Application
import android.util.Log
import android.widget.RatingBar
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import com.example.atleticolineupapp.R
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.roundToInt

//data class SlotData(
//    val key : String,
//    var contentCheck: Boolean = false
//)
@HiltViewModel
class SampleViewModel @Inject constructor() : ViewModel() {

    private val _tabState = MutableStateFlow(false)
    val tabState = _tabState.asStateFlow()
    fun ChangeisTrue() {
        _tabState.value = true
    }
    fun ChangeisFalse(){
        _tabState.value = false
    }
}
@Composable
fun SampleScreen(
    viewModel: SampleViewModel = hiltViewModel()
) {
    val count: Boolean by viewModel.tabState.collectAsState()
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text("$count")
//        ChildA(onClick = { viewModel.inc() })
//        ChildB(count, onClick = { viewModel.inc() })
    }
}

@Composable
fun ChildA(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Log.d("ChildA composed.", "")
        Icon(Icons.Filled.Add, null)
    }
}
@Composable
fun ChildB(count: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Log.d("ChildB composed.", "")
        Icon(Icons.Filled.Add, null)
        Text("$count")
    }
}
@Preview(showBackground = true)
@Composable
fun Ppre(){
    Box(modifier = Modifier.fillMaxSize()){

    }
}