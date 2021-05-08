package mightydanp.industrialtech.api.common.libs;

import net.minecraftforge.common.ToolType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Created by MightyDanp on 3/8/2021.
 */
public class ITToolType{
    public static final ToolType AXE = ToolType.get("axe");
    public static final ToolType HAMMER = ToolType.get("hammer");
    public static final ToolType HOE = ToolType.get("hoe");
    public static final ToolType KNIFE = ToolType.get("knife");
    public static final ToolType PICKAXE = ToolType.get("pickaxe");
    public static final ToolType SHOVEL = ToolType.get("shovel");
    public static final ToolType SOFT_HAMMER = ToolType.get("soft_hammer");
    public static final ToolType WRENCH = ToolType.get("wrench");
    public static final ToolType FILE = ToolType.get("file");
    public static final ToolType CHISEL = ToolType.get("chisel");

    /**
     * Gets the ToolType for the specified name, or creates a new one if none for that name does yet exist.
     * This method can be called during parallel loading
     */

    private final String name;

    private ITToolType(String name){
        this.name = ToolType.get(name).getName();
    }

    public String getName(){
        return name;
    }
}
