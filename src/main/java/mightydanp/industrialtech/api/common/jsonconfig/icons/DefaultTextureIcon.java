package mightydanp.industrialtech.api.common.jsonconfig.icons;

/**
 * Created by MightyDanp on 4/6/2021.
 */
public enum DefaultTextureIcon implements ITextureIcon{
    CUBE("cube"),
    CUBE_SHINY("cube_shiny"),
    DIAMOND("diamond"),
    DULL("dull"),
    EMERALD("emerald"),
    FIERY("fiery"),
    FINE("fine"),
    FLINT("flint"),
    FLUID("fluid"),
    FOOD("food"),
    GAS("gas"),
    GEM_HORIZONTAL("gem_horizontal"),
    GEM_VERTICAL("gem_vertical"),
    GLASS("glass"),
    HEX("hex"),
    LAPIS("lapis"),
    LEAF("leaf"),
    LIGNITE("lignite"),
    MAGNETIC("magnetic"),
    METALLIC("metallic"),
    NETHERSTAR("netherstar"),
    NONE("none"),
    OPAL("opal"),
    PAPER("paper"),
    PLASMA("plasma"),
    POWDER("powder"),
    PRISMARINE("prismarine"),
    QUARTZ("quartz"),
    RAD("rad"),
    REDSTONE("redstone"),
    ROUGH("rough"),
    RUBBER("rubber"),
    RUBY("ruby"),
    SAND("sand"),
    SHARDS("shards"),
    SHINY("shiny"),
    SPACE("space"),
    STONE("stone"),
    WOOD("wood");

    private final String name;

    DefaultTextureIcon(String nameIn) {
        name = nameIn;
    }

    public String getName() {
        return name;
    }

}