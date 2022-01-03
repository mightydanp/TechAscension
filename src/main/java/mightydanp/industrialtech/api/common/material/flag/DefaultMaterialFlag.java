package mightydanp.industrialtech.api.common.material.flag;

import com.mojang.datafixers.util.Pair;

/**
 * Created by MightyDanp on 9/18/2020.
 */
public enum DefaultMaterialFlag implements IMaterialFlag {
    DUST("", "_dust"),
    INGOT("", "_ingot"),
    HOT_INGOT("hot_", "_ingot"),
    SOFTENED_INGOT("softened_", "_ingot"),
    HARDENED_INGOT("hardened_", "_ingot"),
    ORE("", "_ore"),
    SMALL_ORE("", "_ore"),
    GEM("", "_gem"),
    FLUID("", "_fluid"),
    GAS("", "_gas"),
    BLOCK_METAL("", "_block"),
    BLOCK_GEM("", "_block"),

    NULL("", "");

    private final String prefix;
    private final String suffix;

    DefaultMaterialFlag(String prefixIn, String suffixIn) {
        prefix = prefixIn;
        suffix = suffixIn;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public Pair<String, String> getFixes(){
        return new Pair<>(prefix, suffix);
    }
}
/*
    GEM, ORE, DUST, PART, PIPE, WIRE, FOIL, LENS, PLATE, DOUBLE_INGOT, DOUBLE_PLATE, DENSE_PLATE, GAS, SOLID, FLUID, PLASMA,

    G_ALL                       = new TagData[] {GEMS, ORES, EMPTY, DUSTS, PARTS, PIPES, WIRES, FOILS, RAILS, LENSES, STICKS, ARMORS, INGOTS, INGOTS_HOT, PLATES, PLANTS, PROJECTILES, CONTAINERS, DIRTY_DUSTS, MULTIINGOTS, MULTIPLATES, DENSEPLATES, CONTAINERS_GAS, CONTAINERS_SOLID, CONTAINERS_FLUID, CONTAINERS_PLASMA},
    G_CONTAINERS                = new TagData[] {CONTAINERS, PLANTS},
    G_DUST                      = new TagData[] {DUSTS, PLANTS},
    G_DUST_ORES                 = new TagData[] {DUSTS, PLANTS, ORES},
    G_CRYSTAL                   = new TagData[] {PROJECTILES, DUSTS, PLANTS, GEMS},
    G_CRYSTAL_ORES              = new TagData[] {PROJECTILES, DUSTS, PLANTS, GEMS, ORES},
    G_BLAZE                     = new TagData[] {PROJECTILES, DUSTS, PLANTS, STICKS},
    G_PEARL                     = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, GEMS},
    G_PEARL_TRANSPARENT         = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, GEMS, LENSES, TD.Properties.TRANSPARENT},
    G_GLASS                     = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, GEMS, ARMORS, LENSES, TD.Properties.TRANSPARENT},
    G_QUARTZ                    = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, GEMS},
    G_QUARTZ_ORES               = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, GEMS, ORES},
    G_GEM                       = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, GEMS, ARMORS},
    G_GEM_ORES                  = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, GEMS, ARMORS, ORES},
    G_GEM_TRANSPARENT           = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, GEMS, ARMORS, LENSES, TD.Properties.TRANSPARENT},
    G_GEM_ORES_TRANSPARENT      = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, GEMS, ARMORS, LENSES, TD.Properties.TRANSPARENT, ORES},
    G_WOOD                      = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, FOILS},
    G_STONE                     = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, DIRTY_DUSTS},
    G_BRICK                     = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, INGOTS},
    G_INGOT                     = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, ARMORS, INGOTS, INGOTS_HOT, MULTIINGOTS, DENSEPLATES, MULTIPLATES, FOILS},
    G_INGOT_ORES                = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, ARMORS, INGOTS, INGOTS_HOT, MULTIINGOTS, DENSEPLATES, MULTIPLATES, FOILS, ORES},
    G_INGOT_MACHINE             = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, ARMORS, INGOTS, INGOTS_HOT, MULTIINGOTS, DENSEPLATES, MULTIPLATES, FOILS, PARTS},
    G_INGOT_MACHINE_ORES        = new TagData[] {PROJECTILES, DUSTS, PLANTS, PLATES, STICKS, ARMORS, INGOTS, INGOTS_HOT, MULTIINGOTS, DENSEPLATES, MULTIPLATES, FOILS, PARTS, ORES},
    G_INGOT_ND                  = new TagData[] {PROJECTILES, PLATES, STICKS, ARMORS, INGOTS, INGOTS_HOT, MULTIINGOTS, DENSEPLATES, MULTIPLATES, FOILS},
    G_INGOT_ND_MACHINE          = new TagData[] {PROJECTILES, PLATES, STICKS, ARMORS, INGOTS, INGOTS_HOT, MULTIINGOTS, DENSEPLATES, MULTIPLATES, FOILS, PARTS},
    G_MACHINE                   = new TagData[] {PROJECTILES, PLATES, STICKS, ARMORS, WIRES, FOILS, PARTS};

 */