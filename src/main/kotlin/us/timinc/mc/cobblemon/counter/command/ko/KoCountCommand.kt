package us.timinc.mc.cobblemon.counter.command.ko

import com.cobblemon.mod.common.pokemon.Species
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.KoApi
import us.timinc.mc.cobblemon.counter.command.SpeciesCommandExecutor

object KoCountCommand : SpeciesCommandExecutor(listOf("ko", "count")) {
    override fun check(ctx: CommandContext<CommandSourceStack>, player: ServerPlayer, species: Species): Int {
        val score = KoApi.getCount(player, species)
        ctx.source.sendSystemMessage(
            Component.translatable(
                "counter.ko.count",
                player.displayName,
                score,
                species.translatedName
            )
        )
        return score
    }
}