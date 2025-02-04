package org.thinkingstudio.sheet;

import carpet.CarpetServer;
import carpet.fakes.EntityInterface;
import carpet.network.CarpetClient;
import carpet.script.EntityEventsGroup;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingSwapItemsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static carpet.script.CarpetEventServer.Event.PLAYER_DIES;
import static carpet.script.CarpetEventServer.Event.PLAYER_SWAPS_HANDS;

public class SheetModEvents {
    public static void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus) {
        forgeEventBus.addListener(EventPriority.HIGHEST, RegisterCommandsEvent.class, event -> {
            CarpetServer.registerCarpetCommands(event.getDispatcher(), event.getCommandSelection(), event.getBuildContext());
        });
        forgeEventBus.addListener(EventPriority.HIGHEST, PlayerEvent.PlayerLoggedInEvent.class, event -> {
            CarpetServer.onPlayerLoggedIn((ServerPlayer) event.getEntity());
        });
        forgeEventBus.addListener(EventPriority.HIGHEST, LivingSwapItemsEvent.Hands.class, event -> {
            if(PLAYER_SWAPS_HANDS.onPlayerEvent((ServerPlayer) event.getEntity())) {
                event.isCanceled();
            }
        });
        forgeEventBus.addListener(EventPriority.HIGHEST, LivingDeathEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                ((EntityInterface) serverPlayer).getEventContainer().onEvent(EntityEventsGroup.Event.ON_DEATH, event.getSource().getMsgId());
                if (PLAYER_DIES.isNeeded())
                {
                    PLAYER_DIES.onPlayerEvent(serverPlayer);
                }
            }
            ((EntityInterface) event.getEntity()).getEventContainer().onEvent(EntityEventsGroup.Event.ON_DEATH, event.getSource().getMsgId());
        });


        modEventBus.addListener(EventPriority.HIGHEST, RegisterPayloadHandlersEvent.class, event -> {
            final PayloadRegistrar registrar = event.registrar(SheetModReference.MODID);

            registrar.playBidirectional(CarpetClient.CarpetPayload.TYPE, CarpetClient.CarpetPayload.STREAM_CODEC,
                    (payload, context) -> context.handle(payload)
            );
        });
    }
}
