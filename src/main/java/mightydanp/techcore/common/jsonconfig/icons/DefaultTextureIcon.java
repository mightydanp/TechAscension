package mightydanp.techcore.common.jsonconfig.icons;

/**
 * Created by MightyDanp on 4/6/2021.
 */
public enum DefaultTextureIcon {
    CUBE(new TextureIconCodec("cube")),
    CUBE_SHINY(new TextureIconCodec("cube_shiny")),
    DIAMOND(new TextureIconCodec("diamond")),
    DULL(new TextureIconCodec("dull")),
    EMERALD(new TextureIconCodec("emerald")),
    FIERY(new TextureIconCodec("fiery")),
    FINE(new TextureIconCodec("fine")),
    FLINT(new TextureIconCodec("flint")),
    FLUID(new TextureIconCodec("fluid")),
    FOOD(new TextureIconCodec("food")),
    GAS(new TextureIconCodec("gas")),
    GEM_HORIZONTAL(new TextureIconCodec("gem_horizontal")),
    GEM_VERTICAL(new TextureIconCodec("gem_vertical")),
    GLASS(new TextureIconCodec("glass")),
    HEX(new TextureIconCodec("hex")),
    LAPIS(new TextureIconCodec("lapis")),
    LEAF(new TextureIconCodec("leaf")),
    LIGNITE(new TextureIconCodec("lignite")),
    MAGNETIC(new TextureIconCodec("magnetic")),
    METALLIC(new TextureIconCodec("metallic")),
    NETHERSTAR(new TextureIconCodec("netherstar")),
    NONE(new TextureIconCodec("none")),
    OPAL(new TextureIconCodec("opal")),
    PAPER(new TextureIconCodec("paper")),
    PLASMA(new TextureIconCodec("plasma")),
    POWDER(new TextureIconCodec("powder")),
    PRISMARINE(new TextureIconCodec("prismarine")),
    QUARTZ(new TextureIconCodec("quartz")),
    RAD(new TextureIconCodec("rad")),
    REDSTONE(new TextureIconCodec("redstone")),
    ROUGH(new TextureIconCodec("rough")),
    RUBBER(new TextureIconCodec("rubber")),
    RUBY(new TextureIconCodec("ruby")),
    SAND(new TextureIconCodec("sand")),
    SHARDS(new TextureIconCodec("shards")),
    SHINY(new TextureIconCodec("shiny")),
    SPACE(new TextureIconCodec("space")),
    STONE(new TextureIconCodec("stone")),
    WOOD(new TextureIconCodec("wood"));

    private final TextureIconCodec codec;

    DefaultTextureIcon(TextureIconCodec codecIn) {
        codec = codecIn;
    }

    public TextureIconCodec getCodec() {
        return codec;
    }

}