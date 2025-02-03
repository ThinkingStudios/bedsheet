package carpet.mixins;

import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

@Mixin(Explosion.class)
public interface ExplosionAccessor {

    @Accessor
    boolean isFire();

    @Accessor
    Explosion.BlockInteraction getBlockInteraction();

    @Accessor
    Level getLevel();

    @Accessor
    RandomSource getRandom();

    @Accessor(remap = false)
    double getX();

    @Accessor(remap = false)
    double getY();

    @Accessor(remap = false)
    double getZ();

    @Accessor
    float getRadius();

    @Accessor
    Entity getSource();

    @Accessor
    DamageSource getDamageSource();

}
