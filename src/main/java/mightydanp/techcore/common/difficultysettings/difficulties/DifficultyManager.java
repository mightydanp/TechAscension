package mightydanp.techcore.common.difficultysettings.difficulties;


import net.minecraft.world.Difficulty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by MightyDanp on 12/1/2021.
 */
public class DifficultyManager {
    private static Map<Integer, Difficulty> difficultyById = new HashMap<>();

    static {
        for (Difficulty difficulty : Difficulty.values())
            register(difficulty);
    }

    public static void register(Difficulty difficultyIn) {
        Integer id = difficultyIn.getId();
        if (difficultyById.containsKey(difficultyIn.getId()))
            throw new IllegalArgumentException("Difficulty with id already exists: " + id);
        difficultyById.put(id, difficultyIn);
    }

    public static Difficulty getDifficultyById(int iDIn) {
        return difficultyById.get(iDIn);
    }

    public static Set<Difficulty> getAllDifficulty() {
        return new HashSet<Difficulty>(difficultyById.values());
    }
}
