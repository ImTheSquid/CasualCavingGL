package org.engine

import net.arikia.dev.drpc.DiscordEventHandlers
import net.arikia.dev.drpc.DiscordRPC
import net.arikia.dev.drpc.DiscordRichPresence
import net.arikia.dev.drpc.DiscordUser
import org.world.World.getLevel
import org.world.World.subLevel
import java.time.Instant

internal object DiscordHandler {
    private var startTime: Long = 0

    @JvmStatic
    fun init() {
        startTime = Instant.now().toEpochMilli()
        val handlers = DiscordEventHandlers.Builder()
            .setReadyEventHandler { discordUser: DiscordUser -> println("Welcome " + discordUser.username + "#" + discordUser.userId + "!") }
            .build()
        DiscordRPC.discordInitialize("611303571421659137", handlers, true)
    }

    @JvmStatic
    fun shutdown() {
        DiscordRPC.discordShutdown()
    }

    @JvmStatic
    fun updatePresence() {
        val rich = DiscordRichPresence.Builder(if (getLevel() > 0) " Part " + getLevel() else "Not In-Game").setDetails(
            details
        )
            .setBigImage(bigImage, bigImageDetails).setSmallImage(
                smallImage,
                if (Main.getHarold().isInvincible) "Infinite health" else Main.getHarold().health.toString() + " health"
            ).setStartTimestamps(
                startTime
            ).build()
        DiscordRPC.discordUpdatePresence(rich)
    }

    private val details: String
        private get() = bigImageDetails
    private val smallImage: String
        private get() = if (Main.getHarold().isInvincible && Main.getHarold().health > 0) "infinitehearts" else Main.getHarold().health.toString() + if (Main.getHarold().health == 1) "heart" else "hearts"
    private val bigImage: String
        private get() = when (getLevel()) {
            1 -> "overworld"
            2 -> if (subLevel <= 5) "lantern" else "sunstone"
            3 -> if (subLevel <= 1) "insunstone" else "inthecaves"
            4 -> if (subLevel == 5) "redmajor" else "inthecaves"
            5 -> if (subLevel < 2) "meetlarano" else if (subLevel > 2) "insunstone" else "fightlarano"
            6 -> "inthecaves"
            else -> "waiting"
        }
    private val bigImageDetails: String
        private get() = when (getLevel()) {
            -1 -> "Dead"
            0 -> "On Title Screen"
            1 -> "In the Overworld"
            2 -> if (subLevel <= 5) "Entering the Caves" else "Finding the Sun Stone"
            3 -> if (subLevel <= 1) "In the Sun Stone" else "Deep in the Caves"
            4 -> if (subLevel == 5) "Fighting Red Major" else "Deep in the Caves"
            5 -> if (subLevel < 2) "Meeting Larano" else if (subLevel > 2) "Meeting Isolsi" else "Fighting Larano"
            6 -> "Deep in the Caves"
            else -> "Error"
        }
}