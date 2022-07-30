package mightydanp.techcore.common.material.fluid;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.FlowingFluid;

/**
 * Created by MightyDanp on 10/22/2021.
 */
public class TCFluidBlock extends LiquidBlock {
    private final double acceleration;
    public int fluidRGB;

    public TCFluidBlock(java.util.function.Supplier<? extends FlowingFluid> fluid, int fluidRGBIn) {
        this(fluid, 0.014F, fluidRGBIn);
    }

    public TCFluidBlock(java.util.function.Supplier<? extends FlowingFluid> fluid, float accelerationIn, int fluidRGBIn) {
        super(fluid, Block.Properties.of(Material.WATER).friction(100F).speedFactor(accelerationIn).noDrops().noCollission());
        acceleration = accelerationIn;
        fluidRGB = fluidRGBIn;
    }


}