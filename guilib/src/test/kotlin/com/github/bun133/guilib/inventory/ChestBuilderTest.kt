package com.github.bun133.guilib.inventory

import org.junit.jupiter.api.Test

class ChestBuilderTest {
    @Test
    fun buildTest() {
        val scene = chestBuilder(3, null) {
            slot(1, 2) {
                click.watch {
                    println("Clicked!!!")
                }
            }
        }
    }
}