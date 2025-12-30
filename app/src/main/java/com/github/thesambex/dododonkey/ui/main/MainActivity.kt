package com.github.thesambex.dododonkey.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.thesambex.dododonkey.R
import com.github.thesambex.dododonkey.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                MainNavigation()

                CheckAndRequestReadStoragePermissions()
            }
        }
    }

    // Grant permissions from access audio files to media storage
    @Composable
    private fun CheckAndRequestReadStoragePermissions() {
        val context = LocalContext.current
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        var showPermissionDeniedDialog by remember { mutableStateOf(false) }
        var showGoToSettingsDialog by remember { mutableStateOf(false) }

        val requestMediaStoragePermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (!isGranted) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    showGoToSettingsDialog = true
                } else {
                    showPermissionDeniedDialog = true
                }
            }
        }

        LaunchedEffect(Unit) {
            if (ContextCompat.checkSelfPermission(
                    context, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestMediaStoragePermissionLauncher.launch(permission)
            }
        }

        if (showPermissionDeniedDialog) {
            AlertDialog(
                onDismissRequest = { showPermissionDeniedDialog = false },
                title = { Text(stringResource(R.string.permission_rationale_title)) },
                text = { Text(stringResource(R.string.permission_rationale_request_media_storage)) },
                confirmButton = {
                    TextButton(onClick = { showPermissionDeniedDialog = false }) {
                        Text(stringResource(R.string.ok))
                    }
                }
            )
        }

        if (showGoToSettingsDialog) {
            AlertDialog(
                onDismissRequest = { showGoToSettingsDialog = false },
                title = { Text(stringResource(R.string.permission_rationale_title)) },
                text = { Text(stringResource(R.string.permission_rationale_request_media_storage_go_to_settings)) },
                confirmButton = {
                    TextButton(onClick = { showGoToSettingsDialog = false }) {
                        Text(stringResource(R.string.ok))
                    }
                }
            )
        }
    }

}