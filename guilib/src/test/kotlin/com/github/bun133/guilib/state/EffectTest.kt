package com.github.bun133.guilib.state

import org.junit.jupiter.api.Test

class EffectTest {
    @Test
    fun build() {
        val a = Value(3)
        val b = Value(3)
        val c = effect {
            value(a) * value(b)
        }

        assert(c.value() == 9)

        a.value(4)
        assert(c.value() == 12)
    }
}