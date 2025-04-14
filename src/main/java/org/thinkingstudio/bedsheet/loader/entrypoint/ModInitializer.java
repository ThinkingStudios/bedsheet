package org.thinkingstudio.bedsheet.loader.entrypoint;

import net.neoforged.fml.IExtensionPoint;

public interface ModInitializer extends IExtensionPoint {
    void onInitialize();
}
