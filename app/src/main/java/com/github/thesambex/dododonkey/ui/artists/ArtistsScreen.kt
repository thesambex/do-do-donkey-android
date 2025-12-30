package com.github.thesambex.dododonkey.ui.artists

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.github.thesambex.dododonkey.ui.theme.AppTheme

@Composable
fun ArtistsScreen(viewModel: ArtistsVM = hiltViewModel()) {
    ArtistsContent()
}

@Composable
private fun ArtistsContent() {

}

@Preview
@Composable
private fun ArtistsScreenPreview() {
    AppTheme {
        ArtistsContent()
    }
}