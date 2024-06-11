package us.timinc.mc.cobblemon.counter.event

import com.cobblemon.mod.common.api.events.pokemon.PokemonCapturedEvent
import us.timinc.mc.cobblemon.counter.api.CaptureApi

object PokemonCapture {
    fun handle(event: PokemonCapturedEvent) {
        val species = event.pokemon.species.name.lowercase()
        CaptureApi.add(event.player, species)
    }
}