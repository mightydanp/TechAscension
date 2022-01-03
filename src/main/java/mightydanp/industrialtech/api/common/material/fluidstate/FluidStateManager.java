package mightydanp.industrialtech.api.common.material.fluidstate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public class FluidStateManager {
    private static Map<String, IFluidState> fluidStateById = new HashMap<>();

    static {
        for (DefaultFluidState fluidState : DefaultFluidState.values())
            register(fluidState);
    }

    public static void register(IFluidState fluidStateIn) {
        String name = fluidStateIn.getName();
        if (fluidStateById.containsKey(fluidStateIn.getName()))
            throw new IllegalArgumentException("Fluid State with name(" + name + "), already exists.");
        fluidStateById.put(name, fluidStateIn);
    }

    public static IFluidState getFluidStateByName(String nameIn) {
        return fluidStateById.get(nameIn);
    }

    public static Set<IFluidState> getAllFluidState() {
        return new HashSet<IFluidState>(fluidStateById.values());
    }
}