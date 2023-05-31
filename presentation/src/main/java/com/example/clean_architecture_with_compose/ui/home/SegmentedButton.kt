package com.example.clean_architecture_with_compose.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedButton(
    sortState: SortOption,
    onSortStateChanged: (SortOption) -> Unit,
) {
    Row(Modifier.selectableGroup()) {
        Spacer(modifier = Modifier.padding(4.dp))
        SortOption.values().forEach { sortOption ->
            if (sortState == sortOption) {
                Button(
                    onClick = { onSortStateChanged(sortOption) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp)
                ) {
                    Text(text = sortOption.text, color = MaterialTheme.colorScheme.background)
                }
            } else {
                OutlinedButton(
                    onClick = { onSortStateChanged(sortOption) },
                    colors = ButtonDefaults.outlinedButtonColors(
                        disabledContentColor = Color.Red,
                    ),
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp)
                ) {
                    Text(text = sortOption.text, color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}