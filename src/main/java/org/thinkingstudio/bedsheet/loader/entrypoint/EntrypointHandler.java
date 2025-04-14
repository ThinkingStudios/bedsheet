package org.thinkingstudio.bedsheet.loader.entrypoint;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLLoader;

import java.util.Optional;

public class EntrypointHandler {
    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(FMLLoadCompleteEvent.class, event -> {
            ModList.get().forEachModContainer((modId, modContainer) -> {
                Optional<ModInitializer> modInitializer = modContainer.getCustomExtension(ModInitializer.class);
                modInitializer.ifPresent(ModInitializer::onInitialize);
                if (FMLLoader.getDist().isDedicatedServer()) {
                    Optional<DedicatedServerModInitializer> serverModInitializer = modContainer.getCustomExtension(DedicatedServerModInitializer.class);
                    serverModInitializer.ifPresent(DedicatedServerModInitializer::onInitializeServer);
                }
            });
        });
    }
}
