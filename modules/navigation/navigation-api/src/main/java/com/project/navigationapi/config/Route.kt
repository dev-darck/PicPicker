package com.project.navigationapi.config

sealed class Route {
    abstract val routeScheme: String
}

object ProfileRoute : Route() {
    private const val route: String = "profile"
    override val routeScheme: String = route
}

object DownloadRoute : Route() {
    private const val route: String = "download"
    override val routeScheme: String = route
}

object CollectionsRoute : Route() {
    private const val route: String = "collections"
    override val routeScheme: String = route
}

object WebViewRoute : Route() {
    private const val route: String = "webview"
    const val schemeUrl: String = "url"
    const val schemeType: String = "type"
    override val routeScheme: String = "$route/{$schemeUrl}/{$schemeType}"

    fun createRoute(url: String, type: TypeUrl): String = "$route/$url/${type.type}"

    enum class TypeUrl(val type: String) {
        URL_INSIDE("INSIDE"),
        URL_WITH_LOAD_FILE("LOAD_FILE"),
        UNKNOWN("UNKNOWN")
    }
}

object HomeRoute : Route() {
    private const val route: String = "home"
    const val scheme: String = "type"
    override val routeScheme: String = "$route/{$scheme}"

    fun createRoute(type: String): String = "$route/$type"
}

object PhotoDetail : Route() {
    private const val route: String = "photodetail"
    const val scheme: String = "id"
    override val routeScheme: String = "$route/{$scheme}"
    fun createRoute(photoId: String): String = "$route/$photoId"
}

object DefaultRoute : Route() {
    override val routeScheme: String
        get() = "Route"

}