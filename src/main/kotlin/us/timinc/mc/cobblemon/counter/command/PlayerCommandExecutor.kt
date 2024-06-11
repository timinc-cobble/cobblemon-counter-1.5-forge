package us.timinc.mc.cobblemon.counter.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.Commands.literal
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer

abstract class PlayerCommandExecutor(private val path: List<String>) : CommandExecutor() {
    override fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        val reversedPath = path.reversed()
        var lastLink = literal(reversedPath.first())
            .then(argument("player", EntityArgument.player()).executes(::withPlayer))
            .executes(::withoutPlayer)

        for (name in reversedPath.drop(1)) {
            val nextLink = literal(name)
            nextLink.then(lastLink)
            lastLink = nextLink
        }

        dispatcher.register(literal("counter").then(lastLink))
    }

    fun withPlayer(ctx: CommandContext<CommandSourceStack>): Int {
        return check(
            ctx, EntityArgument.getPlayer(ctx, "player")
        )
    }

    fun withoutPlayer(ctx: CommandContext<CommandSourceStack>): Int {
        return ctx.source.player?.let { player ->
            check(
                ctx, player
            )
        } ?: 0
    }

    abstract fun check(ctx: CommandContext<CommandSourceStack>, player: ServerPlayer): Int
}