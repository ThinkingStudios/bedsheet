package org.thinkingstudio.sheet.util;

import cpw.mods.modlauncher.ArgumentHandler;
import cpw.mods.modlauncher.Launcher;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforgespi.language.IModInfo;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class NeoHooks {
    private static String[] launchArgs;

    public static boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    public static Dist getEnvironmentType() {
        return FMLLoader.getDist();
    }

    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static Optional<? extends ModContainer> getModContainer(String modId) {
        return ModList.get().getModContainerById(modId);
    }

    public static String[] getLaunchArguments(boolean sanitize) {
        if (launchArgs == null) {
            try {
                Field argumentHandlerField = Launcher.class.getDeclaredField("argumentHandler");
                argumentHandlerField.setAccessible(true);
                ArgumentHandler handler = (ArgumentHandler) argumentHandlerField.get(Launcher.INSTANCE);
                launchArgs = handler.buildArgumentList();
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
        return launchArgs;
    }

    public static List<IModInfo> getAllMods() {
        return ModList.get().getMods();
    }
}
