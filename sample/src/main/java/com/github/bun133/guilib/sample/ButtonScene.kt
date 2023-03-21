package com.github.bun133.guilib.sample

import com.github.bun133.guilib.Scene
import com.github.bun133.guilib.inventory.chestBuilder
import com.github.bun133.guilib.state.effect
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ButtonScene : Scene() {
    private val scene = chestBuilder(3) {
        slot(1, 1) {
            var b = false
            item.value(ItemStack(Material.GRAY_WOOL))
            effect {
                on(this@slot.click) {
                    it.whoClicked.sendMessage(Component.text("You Clicked Me!"))
                    it.isCancelled = true
                    b = !b
                    this@slot.item.value(ItemStack(if (b) Material.GREEN_WOOL else Material.GRAY_WOOL))
                }
            }
        }
    }

    override fun close(player: Player) {
        scene.close(player)
    }

    override fun open(player: Player) {
        scene.open(player)
    }
}