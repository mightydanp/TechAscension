package mightydanp.techascension.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 1/26/2022.
 */
@Mixin(Block.class)
public abstract class BlockMixin extends BlockBehaviourMixin implements net.minecraftforge.common.extensions.IForgeBlock {

    public boolean isPlaced = false;

    @Inject(method = "setPlacedBy",  // the jvm bytecode signature for the constructor
            at = @At("TAIL")  // signal that this void should be run at the method HEAD, meaning the first opcode
    )
    public void setPlacedBy(Level p_49847_, BlockPos p_49848_, BlockState p_49849_, LivingEntity p_49850_, ItemStack p_49851_, CallbackInfo ci){
        isPlaced = true;
    }
}
