package mightydanp.techascension.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "<init>",  // the jvm bytecode signature for the constructor
            at = @At("TAIL")  // signal that this void should be run at the method HEAD, meaning the first opcode
    )
    public void init(BlockBehaviour.Properties p_60452_, CallbackInfo callbackInfo){
        if(this.getRegistryName() != null) {
            if (this.getRegistryName().getPath().contains("leaf") || this.getRegistryName().getPath().contains("Leaf")) {
                setHasCollision(false);
            }
        }
    }
}
