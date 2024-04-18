package com.zhiwen.navPluginRuntime

data class NavData(
    val route: String,
    val className: String,
    val asStarter: Boolean,
    val type: NavDestination.NavType
)