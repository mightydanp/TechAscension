package mightydanp.industrialtech.api.common.libs;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 4/6/2021.
 */
public enum EnumMaterialTextureFlags {
    CUBE("cube", 0),
    CUBE_SHINY("cube_shiny", 1),
    DIAMOND("diamond", 2),
    DULL("dull", 3),
    EMERALD("emerald", 4),
    FIERY("fiery", 5),
    FINE("fine", 6),
    FLINT("flint", 7),
    FLUID("fluid", 8),
    FOOD("food", 9),
    GAS("gas", 10),
    GEM_HORIZONTAL("gem_horizontal", 11),
    GEM_VERTICAL("gem_vertical", 12),
    GLASS("glass", 13),
    HEX("hex", 14),
    LAPIS("lapis", 15),
    LEAF("leaf", 16),
    LIGNITE("lignite", 17),
    MAGNETIC("magnetic", 18),
    METALLIC("metallic", 19),
    NETHERSTAR("netherstar", 20),
    NONE("none", 21),
    OPAL("opal", 22),
    PAPER("paper", 23),
    PLASMA("plasma", 24),
    POWDER("powder", 25),
    PRISMARINE("prismarine", 26),
    QUARTZ("quartz", 27),
    RAD("rad", 28),
    REDSTONE("redstone", 29),
    ROUGH("rough", 30),
    RUBBER("rubber", 31),
    RUBY("ruby", 32),
    SAND("sand", 33),
    SHARDS("shards", 34),
    SHINY("shiny", 35),
    SPACE("space", 36),
    STONE("stone", 37),
    WOOD("wood", 38);

    private final String name;
    private final float id;
    public List<Pair<String, Float>> enumlist = new ArrayList<>();

    EnumMaterialTextureFlags(String nameIn, float idIn) {
        this.name = nameIn;
        this.id = idIn;
        enumlist.add(new Pair<>(nameIn, idIn));
    }

    public String getName() {
        return this.name;
    }

    public float getID() {
        return this.id;
    }

    public Pair<String, Float> getPair() {
        return new Pair<>(name, id);
    }

    public List<Pair<String, Float>> getEnumlist(){
        return enumlist;
    }

}