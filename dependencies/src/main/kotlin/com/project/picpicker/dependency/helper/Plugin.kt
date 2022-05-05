package com.project.picpicker.dependency.helper

typealias Plug = Sequence<String>

sealed class Plugin(
    open val plugins: Plug = emptySequence()
)

data class LibraryPlugin(
    override val plugins: Plug
): Plugin(plugins)

data class ApplicationPlugin(
    override val plugins: Plug
): Plugin(plugins)

data class MixPlugin(
    override val plugins: Plug = emptySequence()
): Plugin(plugins)

object EmptyPlugins : Plugin(emptySequence())

fun addLibPlug(vararg string: String): LibraryPlugin = LibraryPlugin(string.asSequence())
fun addAppPlug(vararg string: String): ApplicationPlugin = ApplicationPlugin(string.asSequence())

infix operator fun Plugin.plus(plug: Plugin): Plugin = MixPlugin(
    this.plugins.plus(plug.plugins),
)