package com.github.bun133.guilib.state

/**
 * 他の値の変化によって自身の値の変化・再計算等が必要になるオブジェクト
 */
interface Affectable {
    fun dependOn(): List<Value<*>>
    fun onAffected(effect: Effect)
}

/**
 *  他の値の変化によって生じた作用を説明するクラス
 */
abstract class Effect {
    abstract fun effector(): List<Value<*>>
}