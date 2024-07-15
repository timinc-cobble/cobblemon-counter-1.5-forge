package us.timinc.mc.cobblemon.counter.command.capture

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.CaptureApi
import us.timinc.mc.cobblemon.counter.command.PlayerCommandExecutor

object CaptureStreakCommand : PlayerCommandExecutor(listOf("ko", "streak")) {
    override fun check(ctx: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
        val streakData = CaptureApi.getStreak(player)
        val species = PokemonSpecies.getByName(streakData.first) ?: return 0
        val count = streakData.second
        ctx.source.sendSystemMessage(
            Component.translatable(
                "counter.capture.streak",
                player.displayName,
                count,
                species.translatedName
            )
        )
        return count
    }
}