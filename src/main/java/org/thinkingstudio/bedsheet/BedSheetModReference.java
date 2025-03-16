package org.thinkingstudio.bedsheet;

import net.minecraft.resources.ResourceLocation;

public class BedSheetModReference {
    public static final String MODID = "bedsheet";
    public static final String MODNAME = "BedSheet";

    public static ResourceLocation getId(String key) {
        return ResourceLocation.fromNamespaceAndPath(MODID, key);
    }
}
