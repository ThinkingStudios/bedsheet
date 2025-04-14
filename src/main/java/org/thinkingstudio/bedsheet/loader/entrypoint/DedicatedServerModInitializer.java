package org.thinkingstudio.bedsheet.loader.entrypoint;

import net.neoforged.fml.IExtensionPoint;

public interface DedicatedServerModInitializer extends IExtensionPoint {
    void onInitializeServer();
}
