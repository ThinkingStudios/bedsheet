package org.thinkingstudio.sheet;

import carpet.CarpetServer;
import carpet.utils.CarpetRulePrinter;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;

@Mod(SheetModReference.MODID)
public class SheetModEntrypoint {

    public SheetModEntrypoint(IEventBus modEventBus) {
        CarpetServer.onGameStarted();
        SheetModEvents.registerEvents(modEventBus, NeoForge.EVENT_BUS);
        if (FMLLoader.getDist().isDedicatedServer()) {
            CarpetRulePrinter.onInitializeServer();
        }
    }
}
