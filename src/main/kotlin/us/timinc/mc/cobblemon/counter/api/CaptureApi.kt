@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package us.timinc.mc.cobblemon.counter.api

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.pokemon.Species
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import us.timinc.mc.cobblemon.counter.Counter.config
import us.timinc.mc.cobblemon.counter.Counter.info
import us.timinc.mc.cobblemon.counter.store.CaptureCount
import us.timinc.mc.cobblemon.counter.store.CaptureStreak

object CaptureApi {
    fun add(player: ServerPlayer, species: Species, preserveStreak: Boolean = false) {
        add(player, species.resourceIdentifier, preserveStreak)
    }

    fun add(player: ServerPlayer, species: ResourceLocation, preserveStreak: Boolean) {
        add(player, species.path, preserveStreak)
    }

    fun add(player: ServerPlayer, species: String, preserveStreak: Boolean = false) {
        val data = Cobblemon.playerData.get(player)

        val captureCount: CaptureCount = data.extraData.getOrPut(CaptureCount.NAME) { CaptureCount() } as CaptureCount
        val captureStreak: CaptureStreak =
            data.extraData.getOrPut(CaptureStreak.NAME) { CaptureStreak() } as CaptureStreak

        captureCount.add(species)
        if (!preserveStreak) {
            captureStreak.add(species)
        }

        val newCount = captureCount.get(species)
        val newStreak = captureStreak.get(species)

        info(
            "Player ${player.displayName.string} captured a $species streak($newStreak) count($newCount)"
        )
        if (config.broadcastCapturesToPlayer) {
            player.sendSystemMessage(
                Component.translatable(
                    "counter.capture.confirm",
                    Component.translatable("cobblemon.species.${species}.name"),
                    newCount,
                    newStreak
                )
            )
        }

        Cobblemon.playerData.saveSingle(data)
    }

    fun getTotal(player: Player): Int {
        val playerData = Cobblemon.playerData.get(player)
        return (playerData.extraData.getOrPut(CaptureCount.NAME) { CaptureCount() } as CaptureCount).total()
    }

    fun getCount(player: Player, species: Species): Int {
        return getCount(player, species.resourceIdentifier)
    }

    fun getCount(player: Player, species: ResourceLocation): Int {
        return getCount(player, species.path)
    }

    fun getCount(player: Player, species: String): Int {
        val playerData = Cobblemon.playerData.get(player)
        return (playerData.extraData.getOrPut(CaptureCount.NAME) { CaptureCount() } as CaptureCount).get(species)
    }

    fun getStreak(player: Player): Pair<String, Int> {
        val playerData = Cobblemon.playerData.get(player)
        val captureStreakData = (playerData.extraData.getOrPut(CaptureStreak.NAME) { CaptureStreak() } as CaptureStreak)
        return Pair(captureStreakData.species, captureStreakData.count)
    }

    fun resetCount(player: Player) {
        val playerData = Cobblemon.playerData.get(player)
        val captureCountData = (playerData.extraData.getOrPut(CaptureCount.NAME) { CaptureCount() } as CaptureCount)
        captureCountData.reset()
    }

    fun resetStreak(player: Player) {
        val playerData = Cobblemon.playerData.get(player)
        val captureStreakData = (playerData.extraData.getOrPut(CaptureStreak.NAME) { CaptureStreak() } as CaptureStreak)
        captureStreakData.reset()
    }

    fun reset(player: Player) {
        resetCount(player)
        resetStreak(player)
    }
}