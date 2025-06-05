package com.example.chromasync.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chromasync.data.models.AlgoItem
import com.example.chromasync.utils.ThemeManager.toComposeColorScheme
import com.example.chromasync.utils.ThemeManager.toComposeShapes
import com.example.chromasync.utils.ThemeManager.toSimpleComposeTypography
import com.example.chromasync.viewmodel.ThemeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreenViews(viewModel: ThemeViewModel = hiltViewModel()) {

    val algoItems = listOf(AlgoItem(1, "Breadth first search"),
        AlgoItem(2,"Depth first search"),
        AlgoItem(3,"Kahn's algorithm"),
        AlgoItem(4, "Topological sort"),
        AlgoItem(5, "Level order traversal"))

    val currTheme by viewModel.currTheme.collectAsStateWithLifecycle()

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