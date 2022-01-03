package mightydanp.industrialtech.api.common.stonelayer;

/**
 * Created by MightyDanp on 12/29/2021.
 */
public enum DefaultStoneLayer implements IStoneLayer{
    STONE("stone")
    ;

    String name;

    DefaultStoneLayer(String nameIn){
        name = nameIn;
    }
    @Override
    public String getName() {
        return name;
    }
}
