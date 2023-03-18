package com.github.bun133.guilib.state

open class Trigger<V> {
    protected val watcher = mutableListOf<(V) -> Unit>()
    fun watch(f: (V) -> Unit) {
        watcher.add(f)
    }

    fun unwatch(f: (V) -> Unit) {
        watcher.remove(f)
    }
}