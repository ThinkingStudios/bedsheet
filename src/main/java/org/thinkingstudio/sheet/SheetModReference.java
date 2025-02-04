package org.thinkingstudio.sheet;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SheetModReference {
    public static final String MODID = "sheet";
    public static final String MODNAME = "Sheet";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);
    public static ResourceLocation getId(String key) {
        return ResourceLocation.fromNamespaceAndPath(MODID, key);
    }
}
