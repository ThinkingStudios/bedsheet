package org.thinkingstudio.bedsheet.mixins;

import carpet.fakes.BlockPistonBehaviourInterface;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IBlockExtension.class)
public interface IBlockExtensionMixin {
    @Inject(
            method = "isStickyBlock",
            cancellable = true,
            at = @At(
                    value = "HEAD"
            )
    )
    private void isSticky(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof BlockPistonBehaviourInterface behaviourInterface){
            cir.setReturnValue(behaviourInterface.isSticky(state));
        }
    }
}
