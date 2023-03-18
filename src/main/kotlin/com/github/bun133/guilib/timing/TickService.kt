package com.github.bun133.guilib.timing

import com.github.bun133.guilib.GUILib
import com.github.bun133.guilib.guiLib

class TickService(private val guiLib: GUILib) {
    fun everyTick(f: () -> Unit) = tick(1, f)
    fun tick(period: Int, f: () -> Unit) {
        guiLib.plugin.server.scheduler.runTaskTimer(guiLib.plugin, Runnable { f() }, 0, period.toLong())
    }
}

internal fun everyTick(f: () -> Unit) {
    guiLib().tick.everyTick(f)
}

internal fun tick(period: Int, f: () -> Unit) {
    guiLib().tick.tick(period, f)
}