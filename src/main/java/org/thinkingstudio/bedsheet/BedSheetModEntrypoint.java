package org.thinkingstudio.bedsheet;

import carpet.CarpetServer;
import carpet.utils.CarpetRulePrinter;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import org.thinkingstudio.bedsheet.loader.entrypoint.DedicatedServerModInitializer;
import org.thinkingstudio.bedsheet.loader.entrypoint.EntrypointHandler;

@Mod(BedSheetModReference.MODID)
public class BedSheetModEntrypoint {
    public BedSheetModEntrypoint(ModContainer modContainer, IEventBus modEventBus) {
        EntrypointHandler.init(modEventBus);
        if (FMLLoader.getDist().isDedicatedServer()) {
            CarpetServer.onGameStarted();
            BedSheetModEvents.registerEvents(modEventBus, NeoForge.EVENT_BUS);
            modContainer.registerExtensionPoint(DedicatedServerModInitializer.class, new CarpetRulePrinter());
        }
    }
}
