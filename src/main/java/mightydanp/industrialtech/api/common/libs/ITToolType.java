package mightydanp.industrialtech.api.common.libs;

import net.minecraftforge.common.ToolType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Created by MightyDanp on 3/8/2021.
 */
public class ITToolType{
    ToolType toolType;

    private static final Pattern VALID_NAME = Pattern.compile("[^a-z_]"); //Only a-z and _ are allowed, meaning names must be lower case. And use _ to separate words.
    private static final Map<String, ITToolType> VALUES = new ConcurrentHashMap<>();

    public static final ITToolType AXE = get("axe");
    public static final ITToolType HAMMER = get("hammer");
    public static final ITToolType HOE = get("hoe");
    public static final ITToolType KNIFE = get("knife");
    public static final ITToolType PICKAXE = get("pickaxe");
    public static final ITToolType SHOVEL = get("shovel");
    public static final ITToolType SOFT_HAMMER = get("soft_hammer");
    public static final ITToolType WRENCH = get("wrench");

    /**
     * Gets the ToolType for the specified name, or creates a new one if none for that name does yet exist.
     * This method can be called during parallel loading
     */
    public static ITToolType get(String name)
    {
        return VALUES.computeIfAbsent(name, k ->
        {
            if (VALID_NAME.matcher(name).find())
                throw new IllegalArgumentException("ITToolType.get() called with invalid name: " + name);
            return new ITToolType(name);
        });
    }

    private final String name;

    private ITToolType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
