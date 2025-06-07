package com.example.chromasync.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chromasync.R
import com.example.chromasync.data.models.AlgoItem
import com.example.chromasync.utils.SyncState
import com.example.chromasync.utils.ThemeManager.toComposeColorScheme
import com.example.chromasync.utils.ThemeManager.toComposeShapes
import com.example.chromasync.utils.ThemeManager.toSimpleComposeTypography
import com.example.chromasync.viewmodel.ThemeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreenViews(viewModel: ThemeViewModel = hiltViewModel()) {

    val scope = rememberCoroutineScope()

    val algoItems = listOf(AlgoItem(1, "Breadth first search"),
        AlgoItem(2,"Depth first search"),
        AlgoItem(3,"Kahn's algorithm"),
        AlgoItem(4, "Topological sort"),
        AlgoItem(5, "Level order traversal"))

    val currTheme by viewModel.currTheme.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val syncStatus by viewModel.syncState.collectAsStateWithLifecycle()

    when(syncStatus) {
        is SyncState.Syncing ->  {
            Log.d("###", "ListScreenViews: syncing")
            Toast.makeText(context, "Syncing", Toast.LENGTH_LONG).show()
        }
        is SyncState.SyncError -> {
            Log.d("###", "ListScreenViews: sync error")
            Toast.makeText(context, "Syncing Failed, try again", Toast.LENGTH_LONG).show()
        }
        is SyncState.SyncSuccess -> {
            Log.d("###", "ListScreenViews: sync success")
            Toast.makeText(context, "Sync successful", Toast.LENGTH_LONG).show()
        }
        else -> {
            // do nothing
        }
    }


    MaterialTheme(
        colorScheme = currTheme.toComposeColorScheme(),
        typography = currTheme.toSimpleComposeTypography(),
        shapes = currTheme.toComposeShapes(),
    ) {
        Scaffold(
            topBar = {
                // AppBar demonstrating primary color and typography
                TopAppBar(
                    title = {
                        Text(
                            "ChromaSync List",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    actions = {
                        // Theme selector in app bar for easy switching
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_sync_24),
                            contentDescription = "Sync option",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.clickable {
                                viewModel.syncThemes {
                                    Log.d("###", "ListScreenViews: sync items count in callback " + it)
                                    scope.launch(Dispatchers.Main) {
                                        // for number of items synced info
                                        Toast.makeText(context, "Synced items count : " + it, Toast.LENGTH_LONG)
                                        .show()
                                    }
                                }
                            }
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) {p->
            Column(modifier = Modifier.padding(start = 10.dp, top = p.calculateTopPadding())) {
                LazyColumn {
                    items(count=algoItems.size, key={idx-> algoItems[idx].id}, itemContent = {idx->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .height(40.dp)
                        ) {
                            Text(algoItems[idx].name, style = MaterialTheme.typography.titleLarge)
                        }
                    })
                }
            }
        }
    }
}