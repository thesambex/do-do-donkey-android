package com.github.thesambex.dododonkey.ui.artists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.github.thesambex.dododonkey.R
import com.github.thesambex.dododonkey.domain.artists.Artist
import com.github.thesambex.dododonkey.ui.theme.AppTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun ArtistsScreen(viewModel: ArtistsVM = hiltViewModel()) {
    ArtistsLifecycle(viewModel)

    ArtistsContent(
        viewModel.uiState,
        onArtistSelected = {}
    )
}

@Composable
private fun ArtistsContent(
    uiState: ArtistsUiState,
    onArtistSelected: (id: Long) -> Unit
) {
    val artists = uiState.artists?.collectAsLazyPagingItems()

    ConstraintLayout(
        constraintSet = ConstraintSet {
            val title = createRefFor("title")
            constrain(title) {
                top.linkTo(parent.top, 24.dp)
                start.linkTo(parent.start, 16.dp)
            }

            val items = createRefFor("items")
            constrain(items) {
                top.linkTo(title.bottom, 16.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.artists),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            modifier = Modifier.layoutId("title")
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = Modifier.layoutId("items")
        ) {
            items(
                count = artists?.itemCount ?: 0,
                key = { index -> artists?.get(index)?.id ?: index }
            ) { index ->
                artists?.get(index)?.let {
                    ArtistsCard(
                        artist = it,
                        onArtistSelected = onArtistSelected
                    )
                }
            }
        }
    }
}

@Composable
fun ArtistsCard(
    artist: Artist,
    onArtistSelected: (id: Long) -> Unit
) {
    Card(
        shape = RoundedCornerShape(size = 6.dp),
        modifier = Modifier.clickable { onArtistSelected(artist.id) }
    ) {
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier
                .height(165.dp)
                .padding(8.dp)
        ) {
            // TODO: Retrieve artists images and data by API
            AsyncImage(
                model = if (artist.isUnknown()) null else null,
                contentDescription = artist.name
            )
            Text(text = if (artist.isUnknown()) stringResource(R.string.unknown_artist) else artist.name)
        }
    }
}

@Preview
@Composable
private fun ArtistsScreenPreview() {
    AppTheme {
        val artists = listOf(
            Artist(1, "<unknown>"),
            Artist(2, "Sample 1"),
            Artist(3, "Jazz Artists"),
            Artist(4, "Sambariloves"),
            Artist(5, "Eletro Artist"),
            Artist(6, "Rock Master"),
            Artist(7, "Classic Virtuous"),
        )

        ArtistsContent(
            uiState = ArtistsUiState(
                artists = flowOf(PagingData.from(artists))
            ),
            onArtistSelected = {

            }
        )
    }
}