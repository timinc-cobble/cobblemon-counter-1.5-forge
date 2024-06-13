package us.timinc.mc.cobblemon.counter

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract
import net.minecraftforge.event.server.ServerStartedEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import us.timinc.mc.cobblemon.counter.command.Commands
import us.timinc.mc.cobblemon.counter.config.ConfigBuilder
import us.timinc.mc.cobblemon.counter.config.CounterConfig
import us.timinc.mc.cobblemon.counter.event.Events
import us.timinc.mc.cobblemon.counter.event.UseEntity
import us.timinc.mc.cobblemon.counter.store.Stores

@Mod(Counter.MOD_ID)
object Counter {
    const val MOD_ID = "cobbled_counter"

    private var logger: Logger = LogManager.getLogger(MOD_ID)
    var config: CounterConfig = ConfigBuilder.load(CounterConfig::class.java, MOD_ID)
    private var eventsSetup: Boolean = false

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    object Registration {
        @SubscribeEvent
        fun onInit(e: ServerStartedEvent) {
            Stores.register()

            if (!eventsSetup) {
                Events.register()
                eventsSetup = true
            }
        }

        @SubscribeEvent
        fun onRegisterCommands(e: RegisterCommandsEvent) {
            Commands.register(e.dispatcher)
        }

        @SubscribeEvent
        fun onInteract(e: EntityInteract) {
            val player = e.entity
            val world = e.level
            val entity = e.target
            if (player !is Player || e.hand != InteractionHand.MAIN_HAND) {
                return
            }
            e.cancellationResult = UseEntity.handle(player, world, entity)
        }
    }

    fun info(msg: String) {
        if (!config.debug) return
        logger.info(msg)
    }
}