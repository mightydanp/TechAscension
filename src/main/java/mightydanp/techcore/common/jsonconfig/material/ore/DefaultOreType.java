package mightydanp.techcore.common.jsonconfig.material.ore;

/**
 * Created by MightyDanp on 11/27/2021.
 */
public enum DefaultOreType {
    ORE( new OreTypeCodec("ore")),
    GEM( new OreTypeCodec("gem")),
    CRYSTAL( new OreTypeCodec("crystal"))
    ;
    private final OreTypeCodec codec;

    DefaultOreType(OreTypeCodec codecIn){
        codec = codecIn;

    }
    
    public OreTypeCodec getCodec() {
        return codec;
    }
}
