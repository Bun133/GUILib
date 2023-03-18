package com.github.bun133.guilib.state


/**
 * ある値が別の値に依存することを説明するデータを生成するBuilder
 */
class EffectBuilder {
    private val dependTrigger = mutableListOf<Trigger<*>>()
    private val listener = mutableMapOf<Trigger<*>, (Any?) -> Unit>()
    fun <R> value(v: Value<R>): R {
        dependTrigger.add(v)
        return v.value()
    }

    private val triggerListener = mutableMapOf<Trigger<*>, (Any?) -> Unit>()
    private val triggerValue = mutableMapOf<Trigger<*>, Value<*>>()
    fun <V, R> on(t: Trigger<V>, body: (V) -> R): R? {
        dependTrigger.add(t)
        val value = triggerValue[t]
        return if (value == null) {
            val v = Value<R?>(null)
            val f = { it: V ->
                v.value(body(it))
            }
            t.watch(f)
            triggerListener[t] = f as (Any?) -> Unit    // TODO
            triggerValue[t] = v
            null
        } else {
            (value as Value<R>).value()
        }
    }

    fun <V, R> onNotNull(t: Trigger<V>, initValue: R, body: (V) -> R): R {
        dependTrigger.add(t)
        val value = triggerValue[t]
        return if (value == null) {
            val v = Value(initValue)
            val f = { it: V ->
                v.value(body(it))
            }
            t.watch(f)
            triggerListener[t] = f as (Any?) -> Unit    // TODO
            triggerValue[t] = v
            v.value()
        } else {
            (value as Value<R>).value()
        }
    }

    internal fun <R> markDepend(value: Value<R>, calculator: EffectBuilder.() -> R) {
        fun update() {
            value.value(calculator(this))
        }

        dependTrigger.filter { it !in listener.keys }.forEach {
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