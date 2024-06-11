package us.timinc.mc.cobblemon.counter.command.ko

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.KoApi
import us.timinc.mc.cobblemon.counter.command.PlayerCommandExecutor

object KoStreakCommand : PlayerCommandExecutor(listOf("ko", "streak")) {
    override fun check(ctx: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
        val streakData = KoApi.getStreak(player)
        val species = streakData.first
        val count = streakData.second
        ctx.source.sendSystemMessage(Component.translatable("counter.ko.streak", player.displayName, count, species))
        return count
    }
}