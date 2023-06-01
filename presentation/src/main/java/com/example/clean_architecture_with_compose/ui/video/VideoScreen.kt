package com.example.clean_architecture_with_compose.ui.video

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import com.example.clean_architecture_with_compose.extensions.boldString
import com.example.clean_architecture_with_compose.extensions.dateTime
import com.example.clean_architecture_with_compose.extensions.formatSecondsToMinutesAndSeconds
import com.example.clean_architecture_with_compose.ui.common.LoadStateView
import com.example.clean_architecture_with_compose.ui.home.SortOption
import com.example.domain.entity.VideoEntity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VideoScreen(
    viewModel: VideoViewModel,
    query: String,
    sortOption: SortOption,
    modifier: Modifier = Modifier
) {
    Surface {
        viewModel.changeValue(query, sortOption)
        val list = viewModel.videoList.collectAsLazyPagingItems()
        VideoList(items = list, query = query, modifier = modifier)
        LoadStateView(loadState = list.loadState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VideoList(items: LazyPagingItems<VideoEntity>, query: String, modifier: Modifier) {
    val listState = rememberLazyListState()
    if (items.itemCount != 0)
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(start = 4.dp, end = 4.dp)
        ) {
            items(
                count = items.itemCount,
                key = items.itemKey(),
                contentType = items.itemContentType()
            ) { index ->
                val item = items[index]
                if (item != null) {
                    VideoItem(item = item, query = query)
                }
                Divider(color = MaterialTheme.colorScheme.primary)
            }
        }
    else
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(text = "$query 검색 결과가 없어요.")
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VideoItem(item: VideoEntity, query: String) {
    val openLink =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}
    val image = rememberAsyncImagePainter(item.thumbnail)

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
        Row(modifier = Modifier.height(130.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(90.dp)
            ) {
                Image(
                    painter = image,
                    contentDescription = "Thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(5))
                )
                Text(
                    text = item.playTime.formatSecondsToMinutesAndSeconds(), color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                        .background(
                            Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Column(modifier = Modifier.padding(all = 4.dp)) {
                Text(
                    text = item.title.boldString(query = query),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = item.datetime.dateTime())
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = item.author, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}