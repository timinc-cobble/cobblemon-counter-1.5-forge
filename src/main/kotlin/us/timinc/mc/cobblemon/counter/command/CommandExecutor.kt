package us.timinc.mc.cobblemon.counter.command

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack

abstract class CommandExecutor {
    abstract fun register(dispatcher: CommandDispatcher<CommandSourceStack>)
}