package us.timinc.mc.cobblemon.counter.command.capture

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.CaptureApi
import us.timinc.mc.cobblemon.counter.command.SpeciesCommandExecutor

object CaptureCountCommand : SpeciesCommandExecutor(listOf("capture", "count")) {
    override fun check(ctx: CommandContext<CommandSourceStack>, player: ServerPlayer, species: String): Int {
        val score = CaptureApi.getCount(player, species)
        ctx.source.sendSystemMessage(
            Component.translatable(
                "counter.capture.count",
                player.displayName,
                score,
                species
            )
        )
        return score
    }
}