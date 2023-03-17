package com.github.bun133.guilib.inventory

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.jetbrains.annotations.Range

class ChestScene(height: @Range(from = 1, to = 6) Int, title: Component?) : InventoryScene() {
    override val inventory: Inventory =
        if (title != null) Bukkit.createInventory(null, height, title)
        else Bukkit.createInventory(null, height)

    private val slots = generateSlots(height, this)
    override fun getSlotAt(x: @Range(from = 1, to = 9) Int, y: @Range(from = 1, to = 6) Int): ChestSlot? {
        val index = (x - 1) + (y - 1) * 9
        return slots.find { it.location.rawIndex == index }
    }

    override fun slot(x: Int, y: Int) = getSlotAt(x, y)!!

    override fun allSlot(): List<ChestSlot> = slots
}

private fun generateSlots(height: Int, scene: ChestScene): List<ChestSlot> {
    val slots = mutableListOf<ChestSlot>()
    for (x in 1..9) {
        for (y in 1..height) {
            val index = (x - 1) + (y - 1) * 9
            slots.add(
                ChestSlot(
                    SlotLocation(
                        rawIndex = index,
                        x = x, y = y
                    ),
                    scene = scene
                )
            )
        }
    }

    return slots
}

// TODO More Triggers
class ChestSlot(override val location: SlotLocation, override val scene: ChestScene) : Slot()

fun chestBuilder(
    height: @Range(from = 1, to = 6) Int,
    title: Component? = null,
    body: ChestBuilder.() -> Unit
): ChestScene {
    val scene = ChestScene(height, title)
    body(ChestBuilder(scene))
    return scene
}

class ChestBuilder(private val scene: ChestScene) {
    fun slot(x: @Range(from = 1, to = 9) Int, y: @Range(from = 1, to = 6) Int, body: ChestSlot.() -> Unit) {
        body(scene.slot(x, y))
    }
}