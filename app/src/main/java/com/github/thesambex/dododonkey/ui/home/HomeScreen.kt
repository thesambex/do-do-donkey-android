package com.github.thesambex.dododonkey.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.thesambex.dododonkey.R
import com.github.thesambex.dododonkey.ui.artists.ArtistsScreen
import com.github.thesambex.dododonkey.ui.theme.AppTheme

@Composable
fun HomeScreen(navHostController: NavHostController) {
    HomeContent()
}

@Composable
private fun HomeContent() {
    val contentController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(
                onSelectedIndex = {
                    when (it) {
                        0 -> contentController.navigate(HomeScreenRoute.Artists)
                    }
                }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = contentController,
            startDestination = HomeScreenRoute.Artists,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable<HomeScreenRoute.Artists> { ArtistsScreen() }
        }
    }
}

@Composable
private fun BottomBar(onSelectedIndex: (index: Int) -> Unit) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    data class Item(val id: Int, @param:StringRes val name: Int)

    val items = listOf(
        Item(1, R.string.artists)
    )

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    onSelectedIndex(index)
                },
                icon = {},
                label = { Text(stringResource(item.name)) }
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeContent()
    }
}