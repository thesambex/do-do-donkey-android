package com.github.thesambex.dododonkey.ui.artists

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.thesambex.dododonkey.ui.theme.AppTheme
import java.util.UUID

@Composable
fun ArtistsScreen(viewModel: ArtistsVM = hiltViewModel()) {
    ArtistsLifecycle(viewModel)

    ArtistsContent(viewModel.uiState)
}

@Composable
private fun ArtistsContent(uiState: ArtistsUiState) {
    // TODO: Implement Ui
    val artists = uiState.artists?.collectAsLazyPagingItems()
    LazyColumn {
        items(
            count = artists?.itemCount ?: 0,
            key = { index -> artists?.get(index)?.id ?: index }
        ) { index ->
            Text(artists?.get(index)?.name ?: "")
        }
    }
}

@Preview
@Composable
private fun ArtistsScreenPreview() {
    AppTheme {
        ArtistsContent(uiState = ArtistsUiState())
    }
}