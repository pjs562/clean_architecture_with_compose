package com.example.clean_architecture_with_compose.ui.image

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import com.example.clean_architecture_with_compose.ui.common.LoadStateView
import com.example.clean_architecture_with_compose.ui.home.SortOption
import com.example.domain.entity.ImageEntity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ImageScreen(
    viewModel: ImageViewModel,
    query: String,
    sortOption: SortOption
) {
    Surface {
        viewModel.changeValue(query, sortOption)
        val list = viewModel.imageList.collectAsLazyPagingItems()
        ImageGrid(items = list)
        LoadStateView(loadState = list.loadState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ImageGrid(items: LazyPagingItems<ImageEntity>) {

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        content = {
            items(
                count = items.itemCount,
                key = items.itemKey(),
                contentType = items.itemContentType()
            ) { index ->
                items[index]?.let { ImageItem(item = it) }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ImageItem(item: ImageEntity) {
    val openLink =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}
    val image = rememberAsyncImagePainter(item.imageUrl)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(MaterialTheme.shapes.medium),
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.docUrl))
            openLink.launch(intent)
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = image,
                contentDescription = "Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(5))
            )
            if (item.displaySitename.isNotEmpty())
                Text(
                    text = item.displaySitename,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 4.dp, end = 4.dp)
                        .background(
                            Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
        }
    }


}