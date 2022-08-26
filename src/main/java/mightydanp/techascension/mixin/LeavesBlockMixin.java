package mightydanp.techascension.mixin;

import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends BlockMixin{
    @Inject(method = "<init>",  // the jvm bytecode signature for the constructor
            at = @At("TAIL")  // signal that this void should be run at the method HEAD, meaning the first opcode
    )
    public void LeavesBlock(BlockBehaviour.Properties p_54422_, CallbackInfo ci){
        //this.setHasCollision(false);
    }
}
