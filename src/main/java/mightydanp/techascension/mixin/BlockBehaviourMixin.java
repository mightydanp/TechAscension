package mightydanp.techascension.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin extends net.minecraftforge.registries.ForgeRegistryEntry<Block> {
    @Mutable
    @Final
    @Shadow
    protected boolean hasCollision;

    public void setHasCollision(boolean hasCollisionIn){
        hasCollision = hasCollisionIn;
    }
}
