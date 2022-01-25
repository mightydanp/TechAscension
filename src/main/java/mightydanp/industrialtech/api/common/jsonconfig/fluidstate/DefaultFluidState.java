package mightydanp.industrialtech.api.common.jsonconfig.fluidstate;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public enum DefaultFluidState implements IFluidState {
    FLUID("fluid"),
    GAS("gas");

    public String name;

    DefaultFluidState(String nameIn){
        name = nameIn;
    }

    @Override
    public String getName() {
        return name;
    }
}
