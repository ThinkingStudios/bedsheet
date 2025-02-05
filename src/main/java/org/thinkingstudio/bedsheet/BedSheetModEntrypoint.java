package org.thinkingstudio.bedsheet;

import carpet.CarpetServer;
import carpet.utils.CarpetRulePrinter;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;

@Mod(BedSheetModReference.MODID)
public class BedSheetModEntrypoint {

    public BedSheetModEntrypoint(IEventBus modEventBus) {
        CarpetServer.onGameStarted();
        BedSheetModEvents.registerEvents(modEventBus, NeoForge.EVENT_BUS);
        if (FMLLoader.getDist().isDedicatedServer()) {
            CarpetRulePrinter.onInitializeServer();
        }
    }
}
