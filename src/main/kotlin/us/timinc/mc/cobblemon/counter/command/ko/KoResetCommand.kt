package us.timinc.mc.cobblemon.counter.command.ko

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import us.timinc.mc.cobblemon.counter.api.KoApi
import us.timinc.mc.cobblemon.counter.command.ResetCommandExecutor

object KoResetCommand : ResetCommandExecutor("ko") {
    override fun resetCount(ctx: CommandContext<CommandSourceStack>): Int {
        val target = EntityArgument.getPlayer(ctx, "player")
        KoApi.resetCount(target)
        ctx.source.sendSystemMessage(Component.translatable("counter.ko.count.reset", target.name))
        return 0
    }

    override fun resetStreak(ctx: CommandContext<CommandSourceStack>): Int {
        val target = EntityArgument.getPlayer(ctx, "player")
        KoApi.resetStreak(target)
        ctx.source.sendSystemMessage(Component.translatable("counter.ko.streak.reset", target.name))
        return 0
    }

    override fun reset(ctx: CommandContext<CommandSourceStack>): Int {
        val target = EntityArgument.getPlayer(ctx, "player")
        KoApi.reset(target)
        ctx.source.sendSystemMessage(Component.translatable("counter.ko.all.reset", target.name))
        return 0
    }
}