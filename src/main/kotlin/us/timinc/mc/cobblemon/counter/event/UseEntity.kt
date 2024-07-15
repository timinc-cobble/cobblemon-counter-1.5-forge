package us.timinc.mc.cobblemon.counter.event

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import us.timinc.mc.cobblemon.counter.api.EncounterApi

object UseEntity {
    fun handle(player: Player, world: Level, entity: Entity): InteractionResult {
        if (entity !is PokemonEntity) {
            return InteractionResult.PASS
        }
        if (!entity.pokemon.isWild() || world.isClientSide) {
            return InteractionResult.PASS
        }
        if (!player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty) {
            return InteractionResult.PASS
        }
        val species = entity.pokemon.species
        val alreadyEncountered = EncounterApi.check(player, species)
        if (!alreadyEncountered) {
            EncounterApi.add(player, species)
        }
        return InteractionResult.SUCCESS
    }
}