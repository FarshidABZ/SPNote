package com.farshidabz.spnote.presentation.ui

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.farshidabz.spnote.presentation.navigation.SPNoteBottomBar
import com.farshidabz.spnote.presentation.navigation.SPNoteNavHost
import com.farshidabz.spnote.presentation.navigation.bottomNavItems
import com.farshidabz.spnote.presentation.navigation.navigateSingleTopTo
import com.farshidabz.spnote.util.SnackbarManager
import kotlin.let
import kotlin.text.isNotBlank


@Composable
fun SPNoteRoot() {
    val navController = rememberNavController()
    PantryPalApp(navController = navController)
}

@Composable
fun PantryPalApp(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        SnackbarManager.events.collect { event ->
            val message = event.message ?: event.messageResId?.let { context.getString(it) } ?: ""
            val action = event.actionLabelResId?.let { context.getString(it) }
            if (message.isNotBlank()) {
                snackbarHostState.showSnackbar(message = message, actionLabel = action)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            SPNoteBottomBar(
                items = bottomNavItems,
                currentDestination = currentDestination,
                onItemClick = { item ->
                    navController.navigateSingleTopTo(item.route)
                }
            )
        }
    ) { innerPadding ->
        SPNoteNavHost(
            navController = navController,
            innerPadding = innerPadding,
        )
    }
}