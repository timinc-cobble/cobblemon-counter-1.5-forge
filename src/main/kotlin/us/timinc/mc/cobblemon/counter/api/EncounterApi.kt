@file:Suppress("MemberVisibilityCanBePrivate")

package us.timinc.mc.cobblemon.counter.api

import com.cobblemon.mod.common.Cobblemon
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import us.timinc.mc.cobblemon.counter.Counter.config
import us.timinc.mc.cobblemon.counter.Counter.info
import us.timinc.mc.cobblemon.counter.store.Encounter
import us.timinc.mc.cobblemon.counter.util.Util

object EncounterApi {
    fun add(player: Player, species: String) {
        if (check(player, species)) {
            return
        }

        val playerData = Cobblemon.playerData.get(player)
        val encounter: Encounter = playerData.extraData.getOrPut(Encounter.NAME) { Encounter() } as Encounter
        encounter.add(species)
        Cobblemon.playerData.saveSingle(playerData)

        info("Player ${player.displayName.string} encountered a $species")
        if (config.broadcastEncountersToPlayer) {
            player.sendSystemMessage(Component.translatable("counter.encounter.confirm", Component.translatable("cobblemon.species.${Util.cleanSpeciesNameForTranslation(species)}.name")))
        }
    }

    fun getTotal(player: Player): Int {
        val playerData = Cobblemon.playerData.get(player)
        val encounter: Encounter = playerData.extraData.getOrPut(Encounter.NAME) { Encounter() } as Encounter
        return encounter.total()
    }

    fun check(player: Player, species: String): Boolean {
        val playerData = Cobblemon.playerData.get(player)
        val encounter: Encounter = playerData.extraData.getOrPut(Encounter.NAME) { Encounter() } as Encounter
        return encounter.get(species)
    }

    fun reset(player: Player) {
        val playerData = Cobblemon.playerData.get(player)
        val encounter: Encounter = playerData.extraData.getOrPut(Encounter.NAME) { Encounter() } as Encounter
        encounter.reset()
    }
}