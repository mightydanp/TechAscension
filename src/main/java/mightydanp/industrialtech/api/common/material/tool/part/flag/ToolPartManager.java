package mightydanp.industrialtech.api.common.material.tool.part.flag;

import com.mojang.datafixers.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public class ToolPartManager {
    private static Map<Pair<String, String>, IToolPart> toolPartFlagById = new HashMap<>();

    static {
        for (DefaultToolPart toolPartFlag : DefaultToolPart.values())
            register(toolPartFlag);
    }

    public static void register(IToolPart toolPartFlagIn) {
        Pair<String, String> fixes = toolPartFlagIn.getFixes();
        if (toolPartFlagById.containsKey(toolPartFlagIn.getFixes()))
            throw new IllegalArgumentException("Tool Part Flag with prefix(" + fixes.getFirst() + "), and suffix(" + fixes.getSecond() + "), already exists.");
        toolPartFlagById.put(fixes, toolPartFlagIn);
    }

    public static IToolPart getToolPartFlagByFixes(Pair<String, String> fixesIn) {
        return toolPartFlagById.get(fixesIn);
    }

    public static Set<IToolPart> getAllToolPartFlag() {
        return new HashSet<IToolPart>(toolPartFlagById.values());
    }
}