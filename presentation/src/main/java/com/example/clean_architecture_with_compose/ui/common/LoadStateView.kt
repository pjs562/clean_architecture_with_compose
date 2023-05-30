package com.example.clean_architecture_with_compose.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

@Composable
fun LoadStateView(loadState: CombinedLoadStates) {
    when {
        loadState.refresh is LoadState.Loading -> {
            LoadingIndicator()
        }

        loadState.append is LoadState.Loading -> {
            LoadingIndicator()
        }

        loadState.append is LoadState.Error -> {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                action = {
                    TextButton(onClick = { }) {
                        Text("DISMISS")
                    }
                },
            ) {
                Text("This is an error message")
            }
        }
    }
}