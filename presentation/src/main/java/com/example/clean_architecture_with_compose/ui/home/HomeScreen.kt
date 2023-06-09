package com.example.clean_architecture_with_compose.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.clean_architecture_with_compose.R
import com.example.clean_architecture_with_compose.ui.image.ImageScreen
import com.example.clean_architecture_with_compose.ui.image.ImageViewModel
import com.example.clean_architecture_with_compose.ui.video.VideoScreen
import com.example.clean_architecture_with_compose.ui.video.VideoViewModel
import com.example.clean_architecture_with_compose.ui.web.WebScreen
import com.example.clean_architecture_with_compose.ui.web.WebViewModel
import kotlinx.coroutines.launch

enum class SortOption(val text: String, val value: String) {
    Latest("최신순", "recency"),
    Accurate("정확도순", "accuracy")
}

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen() {
    val titles = listOf("웹문서", "동영상", "사진")
    val (searchQuery, setSearchQuery) = rememberSaveable { mutableStateOf("") }
    var query by rememberSaveable { mutableStateOf("") }
    var selectedOption by rememberSaveable { mutableStateOf(SortOption.Accurate) }
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        titles.size
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val topAppBarState = rememberTopAppBarState()
    val scrollpBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val modifier = Modifier.nestedScroll(scrollpBehavior.nestedScrollConnection)

    Scaffold(
        topBar = {
            Column {
                TextField(
                    value = searchQuery,
                    onValueChange = setSearchQuery,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp)),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            query = searchQuery
                            keyboardController?.hide()
                        }
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.daum),
                            contentDescription = "daum",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { setSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    },
                    placeholder = { Text("검색") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
                val coroutineScope = rememberCoroutineScope()
                TabRow(selectedTabIndex = pagerState.currentPage) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(text = title) },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch { pagerState.animateScrollToPage(index) }
                            }
                        )
                    }
                }
                TopAppBar(
                    title = {
                        SegmentedButton(selectedOption) {
                            selectedOption =
                                if (selectedOption == SortOption.Accurate) SortOption.Latest else SortOption.Accurate
                        }
                    }, scrollBehavior = scrollpBehavior
                )
            }
        },
        content = { paddingValues ->
            Column {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(paddingValues),
                ) { index ->
                    when (index) {
                        0 -> {
                            val viewModel: WebViewModel = hiltViewModel()
                            WebScreen(viewModel, query, selectedOption, modifier)
                        }

                        1 -> {
                            val viewModel: VideoViewModel = hiltViewModel()
                            VideoScreen(viewModel, query, selectedOption, modifier)
                        }

                        2 -> {
                            val viewModel: ImageViewModel = hiltViewModel()
                            ImageScreen(viewModel, query, selectedOption, modifier)
                        }

                        else -> throw IllegalArgumentException("Invalid page: $index")
                    }
                }
            }
        }
    )
}