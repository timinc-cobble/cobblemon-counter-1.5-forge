package us.timinc.mc.cobblemon.counter.command.encounter

import com.cobblemon.mod.common.pokemon.Species
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.EncounterApi
import us.timinc.mc.cobblemon.counter.command.SpeciesCommandExecutor

object EncounterCheckCommand : SpeciesCommandExecutor(listOf("encounter", "check")) {
    override fun check(ctx: CommandContext<CommandSourceStack>, player: ServerPlayer, species: Species): Int {
        val check = EncounterApi.check(player, species)
        ctx
            .source
            .sendSystemMessage(
                Component.translatable(
                    "counter.encounter.check" + (if (check) ".positive" else ".negative"),
                    player.displayName,
                    species.translatedName
                )
            )
        return if (check) 1 else 0
    }
}