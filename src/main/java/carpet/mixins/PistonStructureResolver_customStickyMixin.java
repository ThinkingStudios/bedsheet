package carpet.mixins;

import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import carpet.fakes.BlockPistonBehaviourInterface;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(PistonStructureResolver.class)
public class PistonStructureResolver_customStickyMixin {

    @Shadow @Final private Level level;
    @Shadow @Final private Direction pushDirection;

    // NeoForge patched
//    @Shadow private static boolean canStickToEachOther(BlockState blockState, BlockState blockState2) {
//        throw new AssertionError();
//    }
//
//    @Inject(
//        method = "isSticky",
//        cancellable = true,
//        at = @At(
//            value = "HEAD"
//        )
//    )
//    private static void isSticky(BlockState state, CallbackInfoReturnable<Boolean> cir) {
//        if (state.getBlock() instanceof BlockPistonBehaviourInterface behaviourInterface){
//            cir.setReturnValue(behaviourInterface.isSticky(state));
//        }
//    }

    // fields that are needed because @Redirects cannot capture locals
    @Unique private BlockPos pos_addBlockLine;
    @Unique private BlockPos behindPos_addBlockLine;

    @Inject(
        method = "addBlockLine",
        locals = LocalCapture.CAPTURE_FAILHARD,
        at = @At(
            value = "INVOKE",
            ordinal = 1,
            target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
        )
    )
    private void captureBlockLinePositions(BlockPos originPos, Direction direction, CallbackInfoReturnable<Boolean> cir, BlockState blockstate, int i, BlockState oldState, BlockPos behindPos) {
        pos_addBlockLine = behindPos.relative(pushDirection);
        behindPos_addBlockLine = behindPos;
    }

    @Redirect(
        method = "addBlockLine",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;canStickTo(Lnet/minecraft/world/level/block/state/BlockState;)Z"
        )
    )
    private boolean onAddBlockLineCanStickToEachOther(BlockState state, BlockState behindState) {
        if (state.getBlock() instanceof BlockPistonBehaviourInterface behaviourInterface) {
            return behaviourInterface.isStickyToNeighbor(level, pos_addBlockLine, state, behindPos_addBlockLine, behindState, pushDirection.getOpposite(), pushDirection);
        }

        return isAdjacentBlockStuck(state, behindState);
        //return state.canStickTo(behindState) || behindState.canStickTo(state);
    }

    // fields that are needed because @Redirects cannot capture locals
    @Unique private Direction dir_addBranchingBlocks;
    @Unique private BlockPos neighborPos_addBranchingBlocks;

    @Inject(
        method = "addBranchingBlocks",
        locals = LocalCapture.CAPTURE_FAILHARD,
        at = @At(
            value = "INVOKE",
            ordinal = 1,
            target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
        )
    )
    private void captureNeighborPositions(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState state, Direction[] dirs, int i, int j, Direction dir, BlockPos neighborPos) {
        dir_addBranchingBlocks = dir;
        neighborPos_addBranchingBlocks = neighborPos;
    }

    @Redirect(
        method = "addBranchingBlocks",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;canStickTo(Lnet/minecraft/world/level/block/state/BlockState;)Z"
        )
    )
    private boolean onAddBranchingBlocksCanStickToEachOther(BlockState neighborState, BlockState state, BlockPos pos) {
        if (state.getBlock() instanceof BlockPistonBehaviourInterface behaviourInterface) {
            return behaviourInterface.isStickyToNeighbor(level, pos, state, neighborPos_addBranchingBlocks, neighborState, dir_addBranchingBlocks, pushDirection);
        }

        return isAdjacentBlockStuck(neighborState, state);
        //return neighborState.canStickTo(state) && state.canStickTo(neighborState);
    }

    @Unique
    private static boolean isAdjacentBlockStuck(BlockState blockState, BlockState blockState2) {
        if (blockState.is(Blocks.HONEY_BLOCK) && blockState2.is(Blocks.SLIME_BLOCK)) {
            return false;
        } else {
            return (!blockState.is(Blocks.SLIME_BLOCK) || !blockState2.is(Blocks.HONEY_BLOCK)) && (isBlockSticky(blockState) || isBlockSticky(blockState2));
        }
    }

    @Unique
    private static boolean isBlockSticky(BlockState blockState) {
        if (blockState.getBlock() instanceof BlockPistonBehaviourInterface behaviourInterface){
            return behaviourInterface.isSticky(blockState);
        }
        return blockState.is(Blocks.SLIME_BLOCK) || blockState.is(Blocks.HONEY_BLOCK);
    }
}
