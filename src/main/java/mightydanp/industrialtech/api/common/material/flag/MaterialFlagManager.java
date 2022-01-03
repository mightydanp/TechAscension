package mightydanp.industrialtech.api.common.material.flag;

import com.mojang.datafixers.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public class MaterialFlagManager {
    private static Map<Pair<String, String>, IMaterialFlag> materialFlagById = new HashMap<>();

    static {
        for (DefaultMaterialFlag materialFlag : DefaultMaterialFlag.values())
            register(materialFlag);
    }

    public static void register(IMaterialFlag materialFlagIn) {
        Pair<String, String> fixes = new Pair<>(materialFlagIn.getPrefix(), materialFlagIn.getSuffix());
        if (materialFlagById.containsKey(materialFlagIn.getFixes())) {
            throw new IllegalArgumentException("Material Flag with the prefix:(" + materialFlagIn.getPrefix() + "), and the suffix:(" + materialFlagIn.getSuffix() + "), already exists.");
        }
        materialFlagById.put(fixes, materialFlagIn);
    }

    public static IMaterialFlag getMaterialFlagByFixes(Pair<String, String> fixesIn) {
        return materialFlagById.get(fixesIn);
    }

    public static Set<IMaterialFlag> getAllMaterialFlag() {
        return new HashSet<IMaterialFlag>(materialFlagById.values());
    }
}