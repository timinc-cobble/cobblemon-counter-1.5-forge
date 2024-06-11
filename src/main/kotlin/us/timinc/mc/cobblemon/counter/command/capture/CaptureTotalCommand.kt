package us.timinc.mc.cobblemon.counter.command.capture

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.CaptureApi
import us.timinc.mc.cobblemon.counter.command.PlayerCommandExecutor

object CaptureTotalCommand : PlayerCommandExecutor(listOf("capture", "total")) {
    override fun check(ctx: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
        val score = CaptureApi.getTotal(player)
        ctx.source.sendSystemMessage(Component.translatable("counter.capture.total", player.displayName, score))
        return score
    }
}