package org.thinkingstudio.sheet;

import carpet.CarpetServer;
import carpet.network.CarpetClient;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingSwapItemsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static carpet.script.CarpetEventServer.Event.PLAYER_SWAPS_HANDS;

public class SheetModEvents {
    public static void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus) {
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, RegisterCommandsEvent.class, event -> {
            CarpetServer.registerCarpetCommands(event.getDispatcher(), event.getCommandSelection(), event.getBuildContext());
        });
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, PlayerEvent.PlayerLoggedInEvent.class, event -> {
            CarpetServer.onPlayerLoggedIn((ServerPlayer) event.getEntity());
        });
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, LivingSwapItemsEvent.Hands.class, event -> {
            if(PLAYER_SWAPS_HANDS.onPlayerEvent((ServerPlayer) event.getEntity())) {
                event.isCanceled();
            }
        });

        modEventBus.addListener(EventPriority.HIGHEST, RegisterPayloadHandlersEvent.class, event -> {
            final PayloadRegistrar registrar = event.registrar(SheetModReference.MODID);

            registrar.playBidirectional(
                    CarpetClient.CarpetPayload.TYPE, CarpetClient.CarpetPayload.STREAM_CODEC,
                    (payload, context) -> context.handle(payload)
            );
        });
    }
}
