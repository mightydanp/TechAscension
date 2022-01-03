package mightydanp.industrialtech.api.common.material.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateContainer;
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
            registerDefaultState(this.defaultFluidState().getFluidState().setValue(LEVEL, 7));
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
    protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder) {
        super.createFluidStateDefinition(builder);

        if(!source) {
            builder.add(LEVEL);
        }
    }


}
