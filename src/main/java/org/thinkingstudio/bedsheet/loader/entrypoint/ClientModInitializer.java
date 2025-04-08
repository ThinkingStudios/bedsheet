package org.thinkingstudio.bedsheet.loader.entrypoint;

import net.neoforged.fml.IExtensionPoint;

public interface ClientModInitializer extends IExtensionPoint {
    void onInitializeClient();
}
