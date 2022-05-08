package com.project.picpicker

inline fun <reified R> Sequence<Any>.convertTo(): Sequence<R> {
    val typeSet = mutableSetOf<R>()
    forEach {
        if (it is R) {
            typeSet.add(it)
        }
    }
    return typeSet.asSequence()
}