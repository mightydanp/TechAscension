package mightydanp.industrialtech.api.common.libs;

import net.minecraft.util.IStringSerializable;

/**
 * Created by MightyDanp on 3/25/2021.
 */
public enum EnumVeinRarityFlags implements IStringSerializable {
    common("common"),
    uncommon("uncommon"),
    rare("rare");

    @Override
    public String toString()
    {
        return this.name;
    }
    @Override
    public String getString()
    {
        return this.name;
    }

    private final String name;

    private EnumVeinRarityFlags(String nameIn)
    {
        this.name = nameIn;
    }
}