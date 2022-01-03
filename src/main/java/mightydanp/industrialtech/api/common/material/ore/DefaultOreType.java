package mightydanp.industrialtech.api.common.material.ore;

/**
 * Created by MightyDanp on 11/27/2021.
 */
public enum DefaultOreType implements IOreType{
    ORE("ore"),
    GEM("gem"),
    CRYSTAL("crystal")
    ;
    public String name;

    DefaultOreType(String nameIn){
        name = nameIn;

    }

    @Override
    public String getName() {
        return name;
    }
}
