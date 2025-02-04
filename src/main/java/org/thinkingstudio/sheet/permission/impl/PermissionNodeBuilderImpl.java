package org.thinkingstudio.sheet.permission.impl;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.thinkingstudio.sheet.SheetModReference;
import org.thinkingstudio.sheet.permission.api.IPermissionNode;
import org.thinkingstudio.sheet.permission.api.IPermissionNodeBuilder;
import org.thinkingstudio.sheet.util.ObjectHelper;
import org.thinkingstudio.sheet.util.PermissionHooks;

public class PermissionNodeBuilderImpl<T extends IPermissionNode> implements IPermissionNodeBuilder<T> {

    private int level = 0;

    @Override
    public IPermissionNodeBuilder<T> level(int level) {
        this.level = level;
        return this;
    }

    @Override
    public T build(String name) {
        var registryName = SheetModReference.getId(name);
        SheetModReference.LOGGER.debug("Registering Permission '{}'", registryName);
        return ObjectHelper.unsafeCast(PermissionHooks.makeNode(registryName, level));
    }

    public static abstract class NodeImpl implements IPermissionNode {

        private final String key;
        private final ResourceLocation registryName;

        public NodeImpl(ResourceLocation registryName) {
            this.registryName = registryName;
            this.key = registryName.toLanguageKey();
        }

        public String getKey() {
            return key;
        }

        @Override
        public Component getName() {
            return Component.translatable("permission." + key);
        }

        @Override
        public Component getDescription() {
            return Component.translatable("permission." + key + ".desc");
        }

        @Override
        public ResourceLocation getRegistryName() {
            return registryName;
        }
    }
}
