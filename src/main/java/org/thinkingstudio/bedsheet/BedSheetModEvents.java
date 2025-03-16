package org.thinkingstudio.bedsheet;

import carpet.CarpetServer;
import carpet.network.CarpetClient;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class BedSheetModEvents {
    public static void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus) {
        forgeEventBus.addListener(EventPriority.HIGHEST, RegisterCommandsEvent.class, event -> {
            CarpetServer.registerCarpetCommands(event.getDispatcher(), event.getCommandSelection(), event.getBuildContext());
        });
        forgeEventBus.addListener(EventPriority.HIGHEST, PlayerEvent.PlayerLoggedInEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                CarpetServer.onPlayerLoggedIn(serverPlayer);
            }
        });

        modEventBus.addListener(EventPriority.HIGHEST, RegisterPayloadHandlersEvent.class, event -> {
            final PayloadRegistrar registrar = event.registrar(BedSheetModReference.MODID);

            registrar.playBidirectional(CarpetClient.CarpetPayload.TYPE, CarpetClient.CarpetPayload.STREAM_CODEC,
                    (payload, context) -> context.handle(payload)
            );
        });
    }
}
