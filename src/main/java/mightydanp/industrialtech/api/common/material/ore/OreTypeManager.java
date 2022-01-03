package mightydanp.industrialtech.api.common.material.ore;

import java.util.*;

/**
 * Created by MightyDanp on 11/27/2021.
 */
public class OreTypeManager {
    private static Map<String, IOreType> oreTypeById = new HashMap<>();

    static {
        for (DefaultOreType oreType : DefaultOreType.values())
            register(oreType);
    }

    public static void register(IOreType oreTypeIn) {
        String name = oreTypeIn.getName();
        if (oreTypeById.containsKey(oreTypeIn.getName()))
            throw new IllegalArgumentException("Ore Type with name(" + name + "), already exists.");
        oreTypeById.put(name, oreTypeIn);
    }

    public static IOreType getOreTypeByName(String nameIn) {
        return oreTypeById.get(nameIn);
    }

    public static Set<IOreType> getAllOreType() {
        return new HashSet<IOreType>(oreTypeById.values());
    }
}
