package com.project.util.extensions

import android.util.LruCache

fun <T, M> LruCache<T, M>.containsKey(key: T): Boolean {
    return snapshot().containsKey(key)
}

fun <T, M> LruCache<T, M>.add(key: T, value: M) {
    if (get(key) == null) {
        put(key, value)
    }
}