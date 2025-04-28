package org.thinkingstudio.bedsheet;

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
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static carpet.script.CarpetEventServer.Event.PLAYER_SWAPS_HANDS;

public class ModEvents {
    public static void registerEvents(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, RegisterCommandsEvent.class, event -> {
            CarpetServer.registerCarpetCommands(event.getDispatcher(), event.getCommandSelection(), event.getBuildContext());
        });
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, PlayerEvent.PlayerLoggedInEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                CarpetServer.onPlayerLoggedIn(serverPlayer);
            }
        });
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, LivingSwapItemsEvent.Hands.class, event -> {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                if (PLAYER_SWAPS_HANDS.onPlayerEvent(serverPlayer)) {
                    event.isCanceled();
                }
            }
        });

        modEventBus.addListener(EventPriority.HIGHEST, RegisterPayloadHandlersEvent.class, event -> {
            final PayloadRegistrar registrar = event.registrar(ModReference.MODID).executesOn(HandlerThread.MAIN);

            registrar.playBidirectional(CarpetClient.CarpetPayload.TYPE, CarpetClient.CarpetPayload.STREAM_CODEC,
                    (payload, context) -> context.handle(payload)
            );
        });
    }
}
