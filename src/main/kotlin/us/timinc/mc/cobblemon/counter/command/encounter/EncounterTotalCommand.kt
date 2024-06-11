package us.timinc.mc.cobblemon.counter.command.encounter

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.EncounterApi
import us.timinc.mc.cobblemon.counter.command.PlayerCommandExecutor

object EncounterTotalCommand : PlayerCommandExecutor(listOf("encounter", "total")) {
    override fun check(ctx: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
        val score = EncounterApi.getTotal(player)
        ctx.source.sendSystemMessage(Component.translatable("counter.encounter.total", player.displayName, score))
        return score
    }
}