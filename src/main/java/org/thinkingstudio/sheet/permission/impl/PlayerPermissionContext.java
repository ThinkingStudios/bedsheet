package org.thinkingstudio.sheet.permission.impl;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.player.Player;
import org.thinkingstudio.sheet.permission.api.IPermissionContext;

public class PlayerPermissionContext implements IPermissionContext {

    public final Player player;

    public PlayerPermissionContext(Player ep) {
        this.player = Preconditions.checkNotNull(ep, "Player can't be null in PlayerContext!");
    }
}
