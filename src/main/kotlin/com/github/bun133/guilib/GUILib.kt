package com.github.bun133.guilib

import com.github.bun133.guilib.event.EventService
import com.github.bun133.guilib.timing.TickService
import org.bukkit.plugin.java.JavaPlugin

fun guilib(plugin: JavaPlugin) = guiLib(plugin)

/**
 * GUILibの初期化エントリ
 */
fun guiLib(plugin: JavaPlugin) {
    GUILib(plugin)
}

internal fun guiLib() = GUILib.instance!!

class GUILib(val plugin: JavaPlugin) {
    companion object {
        var instance: GUILib? = null
    }

    init {
        instance = this
        setUpService()
    }

    lateinit var event: EventService
    lateinit var tick:TickService

    private fun setUpService() {
        event = EventService(this)
        tick = TickService(this)
    }
}