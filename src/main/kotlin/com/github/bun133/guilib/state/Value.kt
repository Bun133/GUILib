package com.github.bun133.guilib.state

/**
 * 値を表すクラス
 */
open class Value<V>(protected var v: V) : Trigger<V>() {
    open fun value(v: V) {
        this.v = v
        notify()
    }

    protected fun notify(){
        watcher.forEach {
            it(v)
        }
    }

    open fun value(): V {
        return v
    }
}