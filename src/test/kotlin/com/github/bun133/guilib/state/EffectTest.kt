package com.github.bun133.guilib.state

import org.junit.jupiter.api.Test

class EffectTest {
    @Test
    fun valueBuild() {
        val a = Value(3)
        val b = Value(3)
        val c = effect {
            value(a) * value(b)
        }

        assert(c.value() == 9)

        a.value(4)
        assert(c.value() == 12)
    }

    @Test
    fun triggerBuild() {
        val trigger = TestTrigger<Int>()

        val d = effect {
            onNotNull(trigger, "0") {
                it.toString()
            } + " Yen"
        }

        trigger.fire(10)
        assert(d.value() == "10 Yen")

        trigger.fire(100)
        assert(d.value() == "100 Yen")
    }
}

class TestTrigger<V> : Trigger<V>() {
    fun fire(v: V) {
        watcher.forEach {
            it(v)
        }
    }
}