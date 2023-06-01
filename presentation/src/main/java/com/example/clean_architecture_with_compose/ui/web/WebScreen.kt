package com.example.clean_architecture_with_compose.ui.web

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.clean_architecture_with_compose.extensions.boldString
import com.example.clean_architecture_with_compose.extensions.dateTime
import com.example.clean_architecture_with_compose.ui.common.LoadStateView
import com.example.clean_architecture_with_compose.ui.home.SortOption
import com.example.domain.entity.WebEntity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WebScreen(
    viewModel: WebViewModel,
    query: String,
    sortOption: SortOption,
    modifier: Modifier = Modifier,
) {
    Surface {
        viewModel.changeValue(query, sortOption)
        val list = viewModel.webList.collectAsLazyPagingItems()
        WebList(items = list, query = query, modifier = modifier)
        LoadStateView(loadState = list.loadState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WebList(items: LazyPagingItems<WebEntity>, query: String, modifier: Modifier) {
    val listState = rememberLazyListState()
    if (items.itemCount != 0)
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(start = 4.dp, end = 4.dp),
        ) {
            items(
                count = items.itemCount,
                key = items.itemKey(),
                contentType = items.itemContentType(
                )
            ) { index ->
                val item = items[index]
                if (item != null) {
                    WebItem(item = item, query = query)
                }
                Divider(color = MaterialTheme.colorScheme.primary)
            }
        }
    else
        Box(contentAlignment = Alignment.Center ,modifier = Modifier.fillMaxSize().padding(10.dp)){
            Text(text = "$query 검색 결과가 없어요.")
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WebItem(item: WebEntity, query: String) {
    val openLink =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(MaterialTheme.shapes.medium),
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
            openLink.launch(intent)
//            navController.navigate("webview/${Uri.encode(item.url)}")
        }
    ) {
        Column(modifier = Modifier.padding(all = 4.dp)) {
            Text(text = item.title.boldString(query = query))
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = item.url,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = item.contents)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = item.datetime.dateTime())
        }
    }
}