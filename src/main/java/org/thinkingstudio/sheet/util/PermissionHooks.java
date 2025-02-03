package org.thinkingstudio.sheet.util;

import net.neoforged.fml.ModList;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import org.thinkingstudio.sheet.SheetModReference;

import java.util.ArrayList;

// TODO
public class PermissionHooks {
    private static final ArrayList<PermissionNode<?>> PENDING = makeRegisterQueue();

    private static boolean hasPermissionAPI() {
        // in version 1.18, the permission api only available on the service side.
        return ServerLifecycleHooks.getCurrentServer() != null;
    }

    private static ArrayList<PermissionNode<?>> makeRegisterQueue() {
        var modEventBus = ModList.get().getModContainerById(SheetModReference.MODID).orElseThrow().getEventBus();
        modEventBus.addListener(PermissionGatherEvent.Nodes.class, event -> event.addNodes(PENDING));
        return new ArrayList<>();
    }
}
