package com.farshidabz.spnote.presentation.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.farshidabz.spnote.R

val bottomNavItems = listOf(
    BottomNavItem(
        route = Screen.Home.route,
        selectedIconRes = R.drawable.ic_home_filled,
        unselectedIconRes = R.drawable.ic_home,
        labelRes = R.string.bottom_nav_home,
    ),
    BottomNavItem(
        route = Screen.Bookmarks.route,
        selectedIconRes = R.drawable.ic_bookmark_filled,
        unselectedIconRes = R.drawable.ic_bookmark,
        labelRes = R.string.bottom_nav_bookmark,
    ),
    BottomNavItem(
        route = Screen.Setting.route,
        selectedIconRes = R.drawable.ic_setting_filled,
        unselectedIconRes = R.drawable.ic_setting,
        labelRes = R.string.bottom_nav_setting,
    )
)

@Composable
fun SPNoteBottomBar(
    items: List<BottomNavItem>,
    currentDestination: NavDestination?,
    onItemClick: (BottomNavItem) -> Unit,
) {
    val showBottomBar =
        currentDestination?.hierarchy?.any { dest -> items.any { it.route == dest.route } } == true

    if (!showBottomBar) return

    ShortNavigationBar {
        items.forEach { item ->
            val selected =
                currentDestination.hierarchy.any { it.route == item.route }

            ShortNavigationBarItem(
                label = { Text(text = stringResource(id = item.labelRes)) },
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(
                            id = if (selected) item.selectedIconRes else item.unselectedIconRes
                        ),
                        contentDescription = stringResource(item.labelRes)
                    )
                },

                )
        }
    }
}