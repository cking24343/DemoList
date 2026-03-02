package com.sample.permissions.demolist.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.permissions.demolist.presentation.MainActivityViewModel
import com.sample.permissions.demolist.ui.screen.TaskScreen
import com.sample.permissions.demolist.ui.theme.DemoListTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: MainActivityViewModel = koinViewModel()
            val uiState by vm.uiState.collectAsStateWithLifecycle()

            DemoListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskScreen(
                        state = uiState,
                        onEvent = vm::onEvent,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
