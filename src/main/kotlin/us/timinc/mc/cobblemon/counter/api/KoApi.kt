package us.timinc.mc.cobblemon.counter.api

import com.cobblemon.mod.common.Cobblemon
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import us.timinc.mc.cobblemon.counter.Counter.config
import us.timinc.mc.cobblemon.counter.Counter.info
import us.timinc.mc.cobblemon.counter.store.KoCount
import us.timinc.mc.cobblemon.counter.store.KoStreak
import us.timinc.mc.cobblemon.counter.util.Util

object KoApi {
    fun add(player: ServerPlayer, species: String) {
        val data = Cobblemon.playerData.get(player)
        val koCount: KoCount = data.extraData.getOrPut(KoCount.NAME) { KoCount() } as KoCount
        val koStreak: KoStreak = data.extraData.getOrPut(KoStreak.NAME) { KoStreak() } as KoStreak

        koCount.add(species)
        koStreak.add(species)

        val newCount = koCount.get(species)
        val newStreak = koStreak.get(species)

        info(
            "Player ${player.displayName.string} KO'd a $species streak($newStreak) count($newCount)"
        )
        if (config.broadcastKosToPlayer) {
            player.sendSystemMessage(
                Component.translatable(
                    "counter.ko.confirm", Component.translatable("cobblemon.species.${Util.cleanSpeciesNameForTranslation(species)}.name"), newCount, newStreak
                )
            )
        }

        Cobblemon.playerData.saveSingle(data)
    }

    fun getTotal(player: Player): Int {
        val playerData = Cobblemon.playerData.get(player)
        return (playerData.extraData.getOrPut(KoCount.NAME) { KoCount() } as KoCount).total()
    }

    fun getCount(player: Player, species: String): Int {
        val playerData = Cobblemon.playerData.get(player)
        return (playerData.extraData.getOrPut(KoCount.NAME) { KoCount() } as KoCount).get(species)
    }

    fun getStreak(player: Player): Pair<String, Int> {
        val playerData = Cobblemon.playerData.get(player)
        val koStreakData = (playerData.extraData.getOrPut(KoStreak.NAME) { KoStreak() } as KoStreak)
        return Pair(koStreakData.species, koStreakData.count)
    }

    fun resetCount(player: Player) {
        val playerData = Cobblemon.playerData.get(player)
        val koCountData = (playerData.extraData.getOrPut(KoCount.NAME) { KoCount() } as KoCount)
        koCountData.reset()
    }

    fun resetStreak(player: Player) {
        val playerData = Cobblemon.playerData.get(player)
        val koStreakData = (playerData.extraData.getOrPut(KoStreak.NAME) { KoStreak() } as KoStreak)
        koStreakData.reset()
    }

    fun reset(player: Player) {
        resetCount(player)
        resetStreak(player)
    }
}