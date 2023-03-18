package com.github.bun133.guilib

import org.bukkit.entity.Player

/**
 * 画面に表示されているもの全体を表現するクラスです(チェストのGUIとか。)
 */
abstract class Scene {
    /**
     * [player]にこのSceneを開かせます
     */
    abstract fun open(player: Player)

    /**
     * [player]にこのSceneを閉じさせます
     */
    abstract fun close(player: Player)
}