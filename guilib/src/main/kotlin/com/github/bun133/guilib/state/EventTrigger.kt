package com.github.bun133.guilib.state

import com.github.bun133.guilib.event.event
import org.bukkit.event.Event

class EventTrigger<E : Event>(clazz: Class<E>) : Trigger<E>() {
    init {
        event(clazz) {
            watcher.forEach { w ->
                w(it)
            }
        }
    }
}