package org.level

import org.entities.Entity
import org.graphics.Render
import org.loader.ImageResource
import org.world.World.addEntities
import org.world.World.clearEntites
import org.world.World.subLevel
import java.util.concurrent.ConcurrentLinkedQueue

abstract class Level {
    var numSublevels: Int
        private set
    var leftBound = 0f
        protected set
    var rightBound = Render.unitsWide //Points to trigger switch to next sublevel
        protected set
    var leftLimit = -1f
        protected set
    var rightLimit = Render.unitsWide + 1 //Points that entities can't go past
        protected set
    var backgrounds: Array<ImageResource>
        protected set
    var foregrounds: Array<ImageResource?>
        protected set
    var entityRegister = ConcurrentLinkedQueue<Entity>()
        protected set

    constructor(backgrounds: Array<ImageResource>, subLevels: Int) {
        this.backgrounds = backgrounds
        foregrounds = arrayOf()
        numSublevels = subLevels
    }

    data class BiplanarSceneData(val background: ImageResource, val foreground: ImageResource? = null)

    constructor(levelData: Array<BiplanarSceneData>, subLevels: Int) {
        this.backgrounds = levelData.map { data -> data.background }.toTypedArray()
        foregrounds = levelData.map { data -> data.foreground }.toTypedArray()
        numSublevels = subLevels
    }

    abstract fun init()
    abstract val assets: Array<ImageResource>
    abstract fun update(subLevel: Int, deltaTime: Float)
    abstract fun render(subLevel: Int)
    abstract fun renderForeground(subLevel: Int)

    //Execute on transition to next level
    abstract fun cleanup()
    abstract fun reset()
    val entityRegisterArray: Array<Entity>
        get() {
            val registerArr: Array<Any> = entityRegister.toTypedArray()
            val applicable = ArrayList<Entity>()
            for (o in registerArr) {
                if ((o as Entity).subLevel == subLevel) {
                    applicable.add(o)
                }
            }
            return applicable.toTypedArray()
        }

    fun clearEntityRegister() {
        entityRegister.clear()
    }

    protected fun checkHealthVals() {
        for (e in entityRegister) {
            if (e.health <= 0 || e.y + e.height < -10 && !e.isInvincible) {
                e.handleDeath()
                entityRegister.remove(e)
            }
        }
        clearEntites()
        addEntities(entityRegister)
    }
}