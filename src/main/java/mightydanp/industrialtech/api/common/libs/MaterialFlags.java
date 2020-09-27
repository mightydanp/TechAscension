package mightydanp.industrialtech.api.common.libs;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by MightyDanp on 9/18/2020.
 */
public class MaterialFlags {

    private static final Map<String, MaterialFlags> FLAG_MAP = new LinkedHashMap<>();
    public static MaterialFlags DUST = new MaterialFlags("_dust", 0, false);
    public static MaterialFlags INGOT = new MaterialFlags("_ingot", 1, false);
    public static MaterialFlags INGOTHOT = new MaterialFlags("_ingothot", 1, true);
    public static MaterialFlags ORE = new MaterialFlags("_ore", 3, false);
    public static MaterialFlags GEM = new MaterialFlags("_gem", 4, false);
    public static MaterialFlags FLUID = new MaterialFlags("_fluid", -1, true);
    public static MaterialFlags GAS = new MaterialFlags("_gas", -1, true);
    public static MaterialFlags BLOCKMETAL = new MaterialFlags("_block", 14, false);
    public static MaterialFlags BLOCKGEM = new MaterialFlags("_block", 15, false);
    public static MaterialFlags NULL = new MaterialFlags("", -1, false);
    public static MaterialFlags MAGICDYE = new MaterialFlags("_magicdye", -1, true);
    private static int LAST_INTERNAL_ID;
    private long mask;
    private String suffix;
    private String texture;
    private int id;
    private boolean layered;
    private String modid;
    private boolean crafttweaker;

    public MaterialFlags(String suffix, int id, boolean layered) {
        this(suffix, Ref.mod_id + "_materials", id, layered, Ref.mod_id);
    }

    public MaterialFlags(String suffix, String texture, int id, boolean layered, String modid) {
        if (LAST_INTERNAL_ID >= 63) {
            throw new IllegalArgumentException("material flags overloaded! Limit is 64");
        }
        this.mask = 1L << LAST_INTERNAL_ID++;
        this.suffix = suffix;
        this.texture = texture;
        this.id = id;
        this.layered = layered;
        this.modid = modid;
        this.crafttweaker = false;
        if (!suffix.isEmpty()) {
            FLAG_MAP.put(this.getPrefix(), this);
        }
    }

    public MaterialFlags setCraftweaker(boolean craftweaker) {
        this.crafttweaker = true;
        return this;
    }

    public boolean isCrafttweaker() {
        return crafttweaker;
    }

    public String getTexture() {
        return texture;
    }

    public long getMask() {
        return mask;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getPrefix() {
        return suffix.replace("_", "");
    }

    public int getTextureID() {
        return id;
    }

    public boolean isLayered() {
        return layered;
    }

    public String getModID() {
        return this.modid;
    }

    public static boolean hasFlag(String prefix) {
        return FLAG_MAP.containsKey(prefix);
    }

    public static MaterialFlags getFlag(String prefix) {
        return FLAG_MAP.get(prefix);
    }

    public static int getMapSize() {
        return FLAG_MAP.size();
    }
}