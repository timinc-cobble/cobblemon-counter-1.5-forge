package us.timinc.mc.cobblemon.counter.event

import com.cobblemon.mod.common.api.events.pokemon.TradeCompletedEvent
import com.cobblemon.mod.common.util.server
import us.timinc.mc.cobblemon.counter.api.EncounterApi
import java.util.*

object TradeCompleted {
    fun handle(evt: TradeCompletedEvent) {
        val species1 = evt.tradeParticipant1Pokemon.species.name.lowercase(Locale.getDefault())
        val species2 = evt.tradeParticipant2Pokemon.species.name.lowercase(Locale.getDefault())
        val player1 = server()?.playerList?.getPlayer(evt.tradeParticipant1.uuid)
        val player2 = server()?.playerList?.getPlayer(evt.tradeParticipant2.uuid)
        if (player1 != null) {
            EncounterApi.add(player1, species1)
        }
        if (player2 != null) {
            EncounterApi.add(player2, species2)
        }
    }
}