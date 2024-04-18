package com.zhiwen.navPluginRuntime

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class NavDestination(val type: NavType, val route: String, val asStarter: Boolean=false) {
    enum class NavType {
        Fragment,
        Activity,
        Dialog,
        None
    }
}