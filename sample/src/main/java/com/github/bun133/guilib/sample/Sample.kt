package com.github.bun133.guilib.sample

import com.github.bun133.guilib.guiLib
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Sample : JavaPlugin() {
    override fun onEnable() {
        // GUILib Init
        guiLib(this)

        // Plugin startup logic
        getCommand("button")!!.setExecutor { commandSender: CommandSender, command: Command, s: String, strings: Array<String> ->
            if (commandSender is Player) ButtonScene().open(commandSender)
            true
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}