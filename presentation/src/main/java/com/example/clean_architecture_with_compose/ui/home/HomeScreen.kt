package com.example.clean_architecture_with_compose.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
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

    Scaffold(
        topBar = {
            Column {
                TextField(
                    value = searchQuery,
                    onValueChange = setSearchQuery,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
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
                        Button(
                            onClick = {
                                selectedOption =
                                    if (selectedOption == SortOption.Accurate) SortOption.Latest else SortOption.Accurate
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(text = selectedOption.text)
                        }
                    },
                    placeholder = { Text("검색") }
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
                            WebScreen(viewModel, query, selectedOption)
                        }

                        1 -> {
                            val viewModel: VideoViewModel = hiltViewModel()
                            VideoScreen(viewModel, query, selectedOption)
                        }

                        2 -> {
                            val viewModel: ImageViewModel = hiltViewModel()
                            ImageScreen(viewModel, query, selectedOption)
                        }

                        else -> throw IllegalArgumentException("Invalid page: $index")
                    }
                }
            }
        }
    )
}