package org.thinkingstudio.sheet;

import carpet.CarpetServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingSwapItemsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static carpet.script.CarpetEventServer.Event.PLAYER_SWAPS_HANDS;

public class SheetModEvents {
    public static void registerEvents() {
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
    }
}
