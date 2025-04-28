package org.thinkingstudio.bedsheet;

import carpet.CarpetServer;
import carpet.utils.CarpetRulePrinter;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(ModReference.MODID)
public class BedSheetMod {
    public BedSheetMod(ModContainer modContainer, IEventBus modEventBus) {
        CarpetServer.onGameStarted();
        ModEvents.registerEvents(modEventBus);
        if (FMLLoader.getDist().isDedicatedServer()) {
            CarpetRulePrinter.onInitializeServer();
        }
    }
}
