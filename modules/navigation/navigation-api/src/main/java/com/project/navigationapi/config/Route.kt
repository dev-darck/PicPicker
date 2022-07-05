package com.project.navigationapi.config

sealed class Route {
    abstract val routeScheme: String

    companion object {
        const val name = "name"
        const val id = "id"
        const val query = "query"
    }
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
    const val schemeType: String = type
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
    const val scheme: String = type
    override val routeScheme: String = "$route/{$scheme}"

    fun createRoute(type: String): String = "$route/$type"
}

object SettingsRoute : Route() {
    private const val route: String = "settings"
    const val scheme: String = type
    override val routeScheme: String = "$route/{$scheme}"
    fun createRoute(type: String): String = "$route/$type"
}

object PhotoDetailRoute : Route() {
    private const val route: String = "photodetail"
    override val routeScheme: String = "$route/{$id}"
    fun createRoute(photoId: String): String = "$route/$photoId"
}

object CollectionScreenRoute : Route() {
    private const val route: String = "collectionrout"
    const val countPhoto: String = "count"
    const val curatorName: String = "curator"
    override val routeScheme: String = "$route/{$id}/{$name}/{$countPhoto}/{$curatorName}"
    fun createRoute(collectionId: String, collectionName: String, count: String, curator: String): String {
        val collection = collectionName.replace(DELIMITER_NAVIGATION, DELIMITER)
        val name = curator.replace(DELIMITER_NAVIGATION, DELIMITER)
        return "$route/$collectionId/$collection/$count/$name"
    }
}

object SearchScreenRoute : Route() {
    private const val route: String = "searchscreen"
    private const val typeSearch: String = "type_search"
    override val routeScheme: String = "$route/{$query}/{$typeSearch}"
    private const val empty: String = "empty_${route}"
    fun createRoute(query: String = "", typeSearch: String = ""): String {
        return if (query.isEmpty()) "$route/$empty/$empty" else "$route/$query/$typeSearch"
    }

    fun queryNotDefault(query: String): String = query.replace(empty, "")
}

object DefaultRoute : Route() {
    override val routeScheme: String
        get() = "Route"
}

private const val type = "type"
const val DELIMITER = "generated_collection_screen_unique"
const val DELIMITER_NAVIGATION = "/"
