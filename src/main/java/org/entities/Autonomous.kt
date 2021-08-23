package org.entities

abstract class Autonomous(subLevel: Int, spawnX: Float, spawnY: Float) : Entity() {
    @JvmField
    var state = 0 //Controls various states, such as normal movement and melee movement

    init {
        this.subLevel = subLevel
        x = spawnX
        y = spawnY
    }
}