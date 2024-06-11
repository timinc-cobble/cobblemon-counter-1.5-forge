package us.timinc.mc.cobblemon.counter.command.encounter

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.Commands.literal
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import us.timinc.mc.cobblemon.counter.api.EncounterApi
import us.timinc.mc.cobblemon.counter.command.CommandExecutor

object EncounterResetCommand : CommandExecutor() {
    override fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            literal("counter").then(
                literal("encounter").then(
                    literal("reset").requires { source ->
                        source.hasPermission(
                            2
                        )
                    }.then(
                        argument("player", EntityArgument.player()).executes(::reset)
                    )
                )
            )
        )
    }

    fun reset(ctx: CommandContext<CommandSourceStack>): Int {
        val target = EntityArgument.getPlayer(ctx, "player")
        EncounterApi.reset(target)
        ctx.source.sendSystemMessage(Component.translatable("counter.encounter.all.reset", target.name))
        return 0
    }
}