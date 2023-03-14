package com.github.bun133.guilib.state


/**
 * ある値が別の値に依存することを説明するデータを生成するBuilder
 */
class EffectBuilder {
    private val dependOn = mutableListOf<Value<*>>()
    private val listener = mutableMapOf<Value<*>, (Any?) -> Unit>()
    fun <R> value(v: Value<R>): R {
        dependOn.add(v)
        return v.value()
    }

    internal fun <R> markDepend(value: Value<R>, calculator: EffectBuilder.() -> R) {
        fun update() {
            value.value(calculator(this))
        }

        dependOn.filter { it !in listener.keys }.forEach {
            val f = { _: Any? -> update() }
            listener[it] = f
            it.watch(f)
        }
    }
}

fun <R> effect(body: EffectBuilder.() -> R): Value<R> {
    val builder = EffectBuilder()
    val value = Value(body(builder))
    builder.markDepend(value, body)
    return value
}