package org.thinkingstudio.sheet.util;

import carpet.fakes.BlockPistonBehaviourInterface;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 *  NeoForge patched methods on PistonStructureResolver
 */
public class PistonStructureResolverHooks {
    public static boolean isAdjacentBlockStuck(BlockState blockState, BlockState blockState2) {
        if (blockState.is(Blocks.HONEY_BLOCK) && blockState2.is(Blocks.SLIME_BLOCK)) {
            return false;
        } else {
            return (!blockState.is(Blocks.SLIME_BLOCK) || !blockState2.is(Blocks.HONEY_BLOCK)) && (isBlockSticky(blockState) || isBlockSticky(blockState2));
        }
    }

    public static boolean isBlockSticky(BlockState blockState) {
        if (blockState.getBlock() instanceof BlockPistonBehaviourInterface behaviourInterface){
            return behaviourInterface.isSticky(blockState);
        } else {
            return blockState.is(Blocks.SLIME_BLOCK) || blockState.is(Blocks.HONEY_BLOCK);
        }
    }
}
