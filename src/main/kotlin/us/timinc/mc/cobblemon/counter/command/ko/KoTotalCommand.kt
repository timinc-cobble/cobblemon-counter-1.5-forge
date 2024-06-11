package us.timinc.mc.cobblemon.counter.command.ko

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.KoApi
import us.timinc.mc.cobblemon.counter.command.PlayerCommandExecutor

object KoTotalCommand : PlayerCommandExecutor(listOf("ko", "total")) {
    override fun check(ctx: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
        val score = KoApi.getTotal(player)
        ctx.source.sendSystemMessage(Component.translatable("counter.ko.total", player.displayName, score))
        return score
    }
}