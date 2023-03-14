package com.github.bun133.guilib.state

/**
 * 値を表すクラス
 */
open class Value<V>(private var v: V) {
    fun value(v: V) {
        this.v = v
        watcher.forEach {
            it(v)
        }
    }

    fun value(): V {
        return v
    }

    private val watcher = mutableListOf<(V) -> Unit>()
    internal fun watch(f: (V) -> Unit) {
        watcher.add(f)
    }

    internal fun unwatch(f: (V) -> Unit) {
        watcher.remove(f)
    }
}