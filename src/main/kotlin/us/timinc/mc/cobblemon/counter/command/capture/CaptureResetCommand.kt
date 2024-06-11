package us.timinc.mc.cobblemon.counter.command.capture

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import us.timinc.mc.cobblemon.counter.api.CaptureApi
import us.timinc.mc.cobblemon.counter.command.ResetCommandExecutor

object CaptureResetCommand : ResetCommandExecutor("capture") {
    override fun resetCount(ctx: CommandContext<CommandSourceStack>): Int {
        val target = EntityArgument.getPlayer(ctx, "player")
        CaptureApi.resetCount(target)
        ctx.source.sendSystemMessage(Component.translatable("counter.capture.count.reset", target.name))
        return 0
    }

    override fun resetStreak(ctx: CommandContext<CommandSourceStack>): Int {
        val target = EntityArgument.getPlayer(ctx, "player")
        CaptureApi.resetStreak(target)
        ctx.source.sendSystemMessage(Component.translatable("counter.capture.streak.reset", target.name))
        return 0
    }

    override fun reset(ctx: CommandContext<CommandSourceStack>): Int {
        val target = EntityArgument.getPlayer(ctx, "player")
        CaptureApi.reset(target)
        ctx.source.sendSystemMessage(Component.translatable("counter.capture.all.reset", target.name))
        return 0
    }
}