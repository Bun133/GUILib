package com.github.bun133.guilib.state

import com.github.bun133.guilib.event.event
import org.bukkit.event.Event

class EventTrigger<E : Event>(clazz: Class<E>, predicate: (E) -> Boolean = { true }) : Trigger<E>() {
    init {
        event(clazz) {
            if(predicate(it)){
                watcher.forEach { w ->
                    w(it)
                }
            }
        }
    }
}