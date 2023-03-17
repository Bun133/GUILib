package com.github.bun133.guilib.state

/**
 * 値を表すクラス
 */
open class Value<V>(private var v: V) : Trigger<V>() {
    fun value(v: V) {
        this.v = v
        watcher.forEach {
            it(v)
        }
    }

    fun value(): V {
        return v
    }
}