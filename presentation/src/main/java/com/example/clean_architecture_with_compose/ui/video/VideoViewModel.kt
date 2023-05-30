package com.example.clean_architecture_with_compose.ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.clean_architecture_with_compose.ui.home.SortOption
import com.example.domain.usecase.GetVideoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    getVideoListUseCase: GetVideoListUseCase,
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val searchQuery = _searchQuery

    private val _sortOption = MutableStateFlow(SortOption.Accurate)
    private val sortOption = _sortOption

    fun changeValue(value: String, sortOption: SortOption) {
        _searchQuery.value = value
        _sortOption.value = sortOption
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val videoList = combine(_searchQuery, _sortOption) { query, sortOption ->
        Pair(query, sortOption)
    }.flatMapLatest { (query, sortOption) ->
        getVideoListUseCase.invoke(query = query, sort = sortOption.value).cachedIn(viewModelScope)
    }
}