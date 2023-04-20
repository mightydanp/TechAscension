package mightydanp.techcore.common.jsonconfig.fluidstate;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public enum DefaultFluidState {
    FLUID(new FluidStateCodec("fluid")),
    GAS(new FluidStateCodec("gas"));

    public final FluidStateCodec fluidState;

    DefaultFluidState(FluidStateCodec fluidState){
        this.fluidState = fluidState;
    }

    public FluidStateCodec getFluidState() {
        return fluidState;
    }
}
