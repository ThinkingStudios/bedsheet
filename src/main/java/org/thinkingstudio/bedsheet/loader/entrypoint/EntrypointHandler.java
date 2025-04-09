package org.thinkingstudio.bedsheet.loader.entrypoint;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.Optional;

public class EntrypointHandler {
    public static void init() {
        ModList.get().forEachModContainer((modId, modContainer) -> {
            Optional<ModInitializer> modInitializer = modContainer.getCustomExtension(ModInitializer.class);
            modInitializer.ifPresent(ModInitializer::onInitialize);
            if (FMLLoader.getDist().isDedicatedServer()) {
                Optional<DedicatedServerModInitializer> serverModInitializer = modContainer.getCustomExtension(DedicatedServerModInitializer.class);
                serverModInitializer.ifPresent(DedicatedServerModInitializer::onInitializeServer);
            }
            if (FMLLoader.getDist().isClient()) {
                Optional<ClientModInitializer> clientModInitializer = modContainer.getCustomExtension(ClientModInitializer.class);
                clientModInitializer.ifPresent(ClientModInitializer::onInitializeClient);
            }
        });
    }
}
