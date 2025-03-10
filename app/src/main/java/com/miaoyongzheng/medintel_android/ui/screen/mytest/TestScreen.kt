package com.miaoyongzheng.medintel_android.ui.screen.mytest

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview(showBackground = true)
@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    viewModel: TestViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    var isVip by remember { mutableStateOf(false) }
    TestTheme(
        isVip = isVip,
    ) {
        Column {
            Text(
                text = "Hello ${state.name}!",
                modifier = modifier,
                color = MaterialTheme.colorScheme.primary
            )

            Button(onClick = { isVip = !isVip }) {
                Text("click me", color = MaterialTheme.colorScheme.onPrimary)
            }
        }

    }

}