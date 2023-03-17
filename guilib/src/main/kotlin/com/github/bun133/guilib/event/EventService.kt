package com.github.bun133.guilib.event

import com.github.bun133.guilib.GUILib
import com.github.bun133.guilib.guiLib
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class EventService(private val guiLib: GUILib) {
    inline fun <reified E : Event> listen(noinline f: (E) -> Unit) {
        listen(E::class.java, f)
    }

    fun <E : Event> listen(clazz: Class<E>, f: (E) -> Unit) {
        guiLib.plugin.server.pluginManager.registerEvent(
            clazz,
            DummyListener(),
            EventPriority.NORMAL,
            { _, e -> f(e as E) },
            guiLib.plugin
        )
    }
}

private class DummyListener : Listener

internal inline fun <reified E : Event> event(noinline f: (E) -> Unit) {
    guiLib().event.listen(f)
}

internal inline fun <reified E : Event> listen(noinline f: (E) -> Unit) = event(f)

internal fun <E : Event> event(clazz: Class<E>, f: (E) -> Unit) {
    guiLib().event.listen(clazz, f)
}

internal fun <E : Event> listen(clazz: Class<E>, f: (E) -> Unit) = event(clazz, f)