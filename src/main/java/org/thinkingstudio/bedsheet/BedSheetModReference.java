package org.thinkingstudio.bedsheet;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BedSheetModReference {
    public static final String MODID = "bedsheet";
    public static final String MODNAME = "BedSheet";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);
    public static ResourceLocation getId(String key) {
        return ResourceLocation.fromNamespaceAndPath(MODID, key);
    }
}
