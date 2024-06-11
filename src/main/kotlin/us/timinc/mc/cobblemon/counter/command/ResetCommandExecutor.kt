package us.timinc.mc.cobblemon.counter.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.Commands.literal
import net.minecraft.commands.arguments.EntityArgument

abstract class ResetCommandExecutor(val name: String) : CommandExecutor() {
    override fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            literal("counter").then(
                literal(name).then(
                    literal("reset")
                        .requires { source -> source.hasPermission(2) }
                        .then(
                            literal("count")
                                .then(
                                    argument("player", EntityArgument.player())
                                        .executes { resetCount(it) }
                                )
                        )
                        .then(
                            literal("streak")
                                .then(
                                    argument("player", EntityArgument.player())
                                        .executes { resetStreak(it) }
                                )
                        )
                        .then(
                            literal("all").then(
                                argument("player", EntityArgument.player())
                                    .executes { reset(it) }
                            )
                        )
                )
            )
        )
    }

    abstract fun resetCount(ctx: CommandContext<CommandSourceStack>): Int
    abstract fun resetStreak(ctx: CommandContext<CommandSourceStack>): Int
    abstract fun reset(ctx: CommandContext<CommandSourceStack>): Int
}