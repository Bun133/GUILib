package com.github.bun133.guilib.inventory

import com.github.bun133.guilib.Scene
import com.github.bun133.guilib.state.EventTrigger
import com.github.bun133.guilib.state.Value
import com.github.bun133.guilib.timing.everyTick
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.Range

/**
 * インベントリの表示を利用したScene
 */
abstract class InventoryScene : Scene() {
    internal abstract val inventory: Inventory

    override fun open(player: Player) {
        player.openInventory(inventory)
    }

    override fun close(player: Player) {
        if (player.openInventory == inventory) {
            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN)
        }
    }

    /**
     * [x],[y]にある[inventory]のSlotを取得します
     */
    abstract fun getSlotAt(x: Int, y: Int): Slot?

    /**
     * NullAsserted,[getSlotAt]
     */
    open fun slot(x: Int, y: Int) = getSlotAt(x, y)!!

    /**
     * このInventorySceneで利用できるSlotの一覧
     */
    abstract fun allSlot(): List<Slot>
}

/**
 * InventorySceneにおけるスロット
 */
abstract class Slot {
    abstract val location: SlotLocation
    internal abstract val scene: InventoryScene

    // このSlotがクリックされたときのTrigger
    val click = EventTrigger<InventoryClickEvent>(InventoryClickEvent::class.java) { it.inventory == scene.inventory }
    val item = SlotItemValue(this)
}

private fun <E : InventoryEvent> Slot.predicate(e: E) = e.inventory == scene.inventory

data class SlotLocation(
    val rawIndex: Int,
    val x: @Range(from = 1, to = 9) Int,
    val y: @Range(from = 1, to = 6) Int
)

class SlotItemValue(private val slot: Slot) : Value<ItemStack?>(null) {
    init {
        everyTick {
            val now = slot.scene.inventory.getItem(slot.location.rawIndex)
            if (now != value()) {
                value(now)
            }
        }
    }
}