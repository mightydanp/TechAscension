package mightydanp.industrialcore.common.material.fluid;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.fluids.ForgeFlowingFluid;

/**
 * Created by MightyDanp on 10/22/2021.
 */
public class ITFluid extends ForgeFlowingFluid {
    protected final boolean source;
    public int fluidRGB;

    public ITFluid(Properties propertiesIn, boolean sourceIn, int fluidRGBIn) {
        super(propertiesIn);
        this.source = sourceIn;
        if(!source) {
            registerDefaultState(this.defaultFluidState().setValue(LEVEL, 7));
        }

        fluidRGB = fluidRGBIn;
    }



    @Override
    public boolean isSource(FluidState fluidState) {
        return source;
    }

    @Override
    public int getAmount(FluidState fluidState) {
        if(!source) {
            return defaultFluidState().getValue(LEVEL);
        }else {
            return 8;
        }
    }

    @Override
    protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
        super.createFluidStateDefinition(builder);

        if(!source) {
            builder.add(LEVEL);
        }
    }


}
