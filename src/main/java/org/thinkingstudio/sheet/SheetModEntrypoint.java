package org.thinkingstudio.sheet;

import carpet.CarpetServer;
import carpet.utils.CarpetRulePrinter;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(SheetModEntrypoint.MODID)
public class SheetModEntrypoint {
    public static final String MODID = "sheet";

    public SheetModEntrypoint() {
        CarpetServer.onGameStarted();
        if (FMLLoader.getDist().isDedicatedServer()) {
            CarpetRulePrinter.onInitializeServer();
        }

        SheetModEvents.registerEvents();
    }
}
