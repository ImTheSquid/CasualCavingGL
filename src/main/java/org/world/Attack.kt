package org.world

import org.engine.Main
import org.entities.Entity
import org.entities.Harold
import org.entities.aggressive.LaranoStalactite
import org.world.World.entities
import org.world.World.subLevel

object Attack {
    //Returns true if attack was successful
    @JvmStatic
    fun melee(e: Entity, damage: Int, range: Float): Boolean {
        if (e is Harold || e is LaranoStalactite) { //Check if player is executing melee
            val applicable = sortRegister(e) //Finds entities in range
            for (x in applicable) {
                if (x === e || x.y > e.y + e.height || x.y + x.height < e.y) continue  //Skip if y-val is out of range
                if (e.isFacingRight && e.x + e.width + range >= x.x) {
                    x.doDamage(e, damage)
                    return true
                } else if (!e.isFacingRight && x.x + x.width + range >= e.x) {
                    x.doDamage(e, damage)
                    return true
                }
            }
            if (e is LaranoStalactite) doHaroldAttack(e, damage, range)
        } else {
            doHaroldAttack(e, damage, range)
        }
        return false
    }

    private fun doHaroldAttack(e: Entity, damage: Int, range: Float): Boolean {
        val x: Entity = Main.getHarold()
        if (x.y > e.y + e.height || x.y + x.height < e.y) return false //Return if y-val is out of range
        if (e.isFacingRight && e.x + e.width + range >= x.x) {
            x.doDamage(e, damage)
            return true
        } else if (!e.isFacingRight && x.x + x.width + range >= e.x) {
            x.doDamage(e, damage)
            return true
        }
        return false
    }

    private fun sortRegister(e: Entity): Array<Entity> {
        val levelReg: Array<Entity> = entities.toTypedArray()
        val applicable = ArrayList<Entity>()
        for (y in levelReg) {
            val x = y
            if (x === e) continue  //Skip if entity is itself
            if (e.isFacingRight && x.x >= e.x) {
                applicable.add(x)
            } else if (!e.isFacingRight && x.x <= e.x) {
                applicable.add(x)
            }
        }
        applicable.removeIf { n: Entity -> n.subLevel != subLevel }
        return applicable.toTypedArray()
    }
}