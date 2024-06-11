package us.timinc.mc.cobblemon.counter.event

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import us.timinc.mc.cobblemon.counter.Counter.config
import us.timinc.mc.cobblemon.counter.api.CaptureApi
import us.timinc.mc.cobblemon.counter.api.EncounterApi

object UseEntity {
    fun handle(player: Player, world: Level, entity: Entity): InteractionResult {
        if (entity !is PokemonEntity) {
            return InteractionResult.PASS
        }
        if (!entity.pokemon.isWild()) {
            return InteractionResult.PASS
        }
        if (!player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty) {
            return InteractionResult.PASS
        }
        if (!world.isClientSide) {
            val species = entity.pokemon.species.name.lowercase()
            val alreadyEncountered = EncounterApi.check(player, species)
            if (alreadyEncountered) {
                if (config.broadcastEncountersToPlayer) {
                    val alreadyCaught = CaptureApi.getCount(player, species) > 0
                    player.sendSystemMessage(
                        Component.translatable(
                            if (alreadyCaught) "counter.encounter.alreadyCaught" else "counter.encounter.alreadyEncountered",
                            species
                        )
                    )
                }
            } else {
                EncounterApi.add(player, species)
            }
        }
        return InteractionResult.SUCCESS
    }
}