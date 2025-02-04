package org.thinkingstudio.sheet.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContext;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContextKey;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes;
import org.thinkingstudio.sheet.SheetModReference;
import org.thinkingstudio.sheet.permission.api.IPermissionContext;
import org.thinkingstudio.sheet.permission.api.IPermissionNode;
import org.thinkingstudio.sheet.permission.impl.PermissionNodeBuilderImpl;
import org.thinkingstudio.sheet.permission.impl.PlayerPermissionContext;
import org.thinkingstudio.sheet.permission.impl.TargetPermissionContext;

import java.util.ArrayList;

// TODO: NOT FINISH
public class PermissionHooks {
    private static final ArrayList<PermissionNode<?>> PENDING = makeRegisterQueue();

    private static final PermissionDynamicContextKey<Entity> TARGET = new PermissionDynamicContextKey<>(Entity.class, "target", Object::toString);
    private static final PermissionDynamicContextKey<Player> PLAYER = new PermissionDynamicContextKey<>(Player.class, "player", Object::toString);

    private static final PermissionDynamicContextKey<CommandSourceStack> COMMAND_SOURCE_STACK = new PermissionDynamicContextKey<>(CommandSourceStack.class, "command_source_stack", Object::toString);

    public static IPermissionNode makeNode(ResourceLocation registryName, int level) {
        var node = new PermissionNode<>(registryName, PermissionTypes.BOOLEAN, (player, uuid, contexts) -> true, TARGET, PLAYER, COMMAND_SOURCE_STACK);
        var nodeImpl = new PermissionNodeBuilderImpl.NodeImpl(registryName) {

            @Override
            public boolean resolve(Player player, IPermissionContext context) {
                if (!hasPermissionAPI()) {
                    return true;
                }
                if (player instanceof ServerPlayer) {
                    return PermissionAPI.getPermission((ServerPlayer) player, node, makeContexts(context));
                }
                return super.resolve(player, context);
            }

            @Override
            public boolean resolve(GameProfile profile, IPermissionContext context) {
                if (!hasPermissionAPI()) {
                    return true;
                }
                return PermissionAPI.getOfflinePermission(profile.getId(), node, makeContexts(context));
            }
        };
        node.setInformation(nodeImpl.getName(), nodeImpl.getDescription());
        PENDING.add(node);
        return nodeImpl;
    }

    private static PermissionDynamicContext<?>[] makeContexts(IPermissionContext context) {
        var contexts = new ArrayList<PermissionDynamicContext<?>>();
        if (context instanceof PlayerPermissionContext player && player.player != null) {
            contexts.add(PLAYER.createContext(player.player));
        }
        if (context instanceof TargetPermissionContext target && target.target != null) {
            contexts.add(TARGET.createContext(target.target));
        }
        
        return contexts.toArray(new PermissionDynamicContext<?>[0]);
    }

    private static boolean hasPermissionAPI() {
        // in version 1.18, the permission api only available on the service side.
        return ServerLifecycleHooks.getCurrentServer() != null;
    }

    private static ArrayList<PermissionNode<?>> makeRegisterQueue() {
        var modEventBus = ModList.get().getModContainerById(SheetModReference.MODID).orElseThrow().getEventBus();
        if (modEventBus != null) {
            modEventBus.addListener(PermissionGatherEvent.Nodes.class, event -> event.addNodes(PENDING));
        }
        return new ArrayList<>();
    }
}
