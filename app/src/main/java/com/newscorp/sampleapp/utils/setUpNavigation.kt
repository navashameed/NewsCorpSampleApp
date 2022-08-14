package com.newscorp.sampleapp.utils

import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

fun Toolbar.setUpNavigation(navController: NavController) {
    val appBarConfig = AppBarConfiguration(navController.graph)
    NavigationUI.setupWithNavController(this, navController, appBarConfig)
}